package br.com.skywars.core.command.engine.command.node;

import br.com.skywars.core.command.engine.command.help.HelpNode;
import br.com.skywars.core.command.engine.command.paramter.ParamProcessor;
import br.com.skywars.core.command.engine.command.Command;
import br.com.skywars.core.command.engine.command.CommandHandler;
import br.com.skywars.core.command.engine.command.bukkit.BukkitCommand;
import br.com.skywars.core.command.engine.command.paramter.Param;
import br.com.skywars.core.command.engine.command.utils.CommandReflection;
import lombok.Getter;
import lombok.SneakyThrows;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public final class CommandNode {

    @Getter private static final List<CommandNode> nodes = new ArrayList<>();
    @Getter private static final HashMap<Class<?>, Object> instances = new HashMap<>();

    // Command information
    private final ArrayList<String> names = new ArrayList<>();
    private final String permission;
    private final String description;
    private final boolean async;

    // Executor information
    private final boolean playerOnly;
    private final boolean consoleOnly;

    // Reflect information
    private final Object parentClass;
    private final Method method;

    // Arguments information
    private final List<ArgumentNode> parameters = new ArrayList<>();

    // The help nodes associated with this node
    private final List<HelpNode> helpNodes = new ArrayList<>();

    public CommandNode(Object parentClass, Method method, Command command) {
        // Loads names
        Arrays.stream(command.names()).forEach(name -> names.add(name.toLowerCase()));

        // Retrieve information from annotation
        this.permission = command.permission();
        this.description = command.description();
        this.async = command.async();
        this.playerOnly = command.playerOnly();
        this.consoleOnly = command.consoleOnly();

        // Reflection
        this.parentClass = parentClass;
        this.method = method;

        // Register all the argument nodes
        Arrays.stream(method.getParameters()).forEach(parameter -> {
            Param param = parameter.getAnnotation(Param.class);
            if(param == null) return;

            parameters.add(new ArgumentNode(param.name(), param.concated(), param.required(), parameter));
        });

        // Register bukkit command

        names.forEach(name -> {
            if(!BukkitCommand.getCommands().containsKey(name.split(" ")[0].toLowerCase())) new BukkitCommand(name.split(" ")[0].toLowerCase());
        });

        // Makes it so you can use /plugin:command
        List<String> toAdd = new ArrayList<>();
        names.forEach(name -> toAdd.add(CommandHandler.getPluginName() + ":" + name.toLowerCase()));
        names.addAll(toAdd);

        // Add node to array list
        nodes.add(this);
    }

    /**
     * Gets the probably that a player is referring to
     * this command whenever executing a command
     * @param args Arguments
     * @return Match Probability
     */
    public int getMatchProbability(Object sender, String label, String[] args, boolean tabbed) {
        AtomicInteger probability = new AtomicInteger(0);

        this.names.forEach(name -> {
            StringBuilder nameLabel = new StringBuilder(label).append(" ");
            String[] splitName = name.split(" ");
            int nameLength = splitName.length;

            for(int i = 1; i < nameLength; i++)
                if(args.length>= i) nameLabel.append(args[i - 1]).append(" ");

            if(name.equalsIgnoreCase(nameLabel.toString().trim())) {
                int requiredParameters = (int) this.parameters.stream()
                        .filter(ArgumentNode::isRequired)
                        .count();

                int actualLength = args.length - (nameLength - 1);

                if(requiredParameters == actualLength || parameters.size() == actualLength) {
                    probability.addAndGet(125);
                    return;
                }

                if(this.parameters.size() > 0) {
                    ArgumentNode lastArgument = this.parameters.get(this.parameters.size() - 1);
                    if (lastArgument.isConcated() && actualLength > requiredParameters) {
                        probability.addAndGet(125);
                        return;
                    }
                }

                if(!tabbed || splitName.length > 1 || parameters.size() > 0)
                    probability.addAndGet(50);

                if(actualLength > requiredParameters)
                    probability.addAndGet(15);

                if(CommandReflection.isPlayer(sender) && consoleOnly)
                    probability.addAndGet(-15);

                if(!CommandReflection.isPlayer(sender) && playerOnly)
                    probability.addAndGet(-15);

                if(!permission.equals("") && !CommandReflection.hasPermission(sender, permission))
                    probability.addAndGet(-15);

                return;
            }

            String[] labelSplit = nameLabel.toString().split(" ");
            for(int i = 0; i < nameLength && i < labelSplit.length; i++)
                if(splitName[i].equalsIgnoreCase(labelSplit[i]))
                    probability.addAndGet(5);
        });

        return probability.get();
    }

    /**
     * Sends a player the usage message of this command
     */
    public void sendUsageMessage(Object sender) {
        if(consoleOnly && CommandReflection.isPlayer(sender)) {
            CommandReflection.sendMessage(sender, "§cApenas o sistema pode executar este comando.");
            return;
        }

        if(playerOnly && !CommandReflection.isPlayer(sender)) {
            CommandReflection.sendMessage(sender, "§cComando disponível apenas in-game.");
            return;
        }

        if(!permission.equals("") && !CommandReflection.hasPermission(sender, permission)) {
            CommandReflection.sendMessage(sender, "§cVocê não possui permissão para executar este comando.");
            return;
        }

        StringBuilder builder = new StringBuilder("§cUsage: /" + names.get(0) + " ");
        parameters.forEach(param -> {
            if(param.isRequired()) builder.append("<").append(param.getName()).append(param.isConcated() ? ".." : "").append(">");
            else builder.append("[").append(param.getName()).append(param.isConcated() ? ".." : "").append("]");
            builder.append(" ");
        });

        // Sends the usage message
        CommandReflection.sendMessage(sender, builder.toString());
    }

    /**
     * Gets the required arguments length
     * @return Required Length
     */
    public int requiredArgumentsLength() {
        int requiredArgumentsLength = names.get(0).split(" ").length - 1;
        for(ArgumentNode node : parameters) if(node.isRequired()) requiredArgumentsLength++;
        return requiredArgumentsLength;
    }

    /**
     * Executes the command
     *
     * @param sender Sender
     * @param args Arguments
     */
    @SneakyThrows
    public void execute(Object sender, String[] args) {
        // Checks if the player has permission
        if(!permission.equals("") && !CommandReflection.hasPermission(sender, permission)) {
            CommandReflection.sendMessage(sender, "§cVocê não possui permissão para executar este comando.");
            return;
        }

        // Checks if command is console only
        if(!CommandReflection.isPlayer(sender) && playerOnly) {
            CommandReflection.sendMessage(sender, "§cComando disponível apenas in-game.");
            return;
        }

        // Checks if command is player only
        if(CommandReflection.isPlayer(sender) && consoleOnly) {
            CommandReflection.sendMessage(sender, "§cApenas o sistema pode executar este comando.");
            return;
        }

        // Calculates the amount of arguments in the name
        int nameArgs = (names.get(0).split(" ").length - 1);

        List<Object> objects = new ArrayList<>(Collections.singletonList(sender));
        for(int i = 0; i < args.length - nameArgs; i++) {
            if(parameters.size() < i + 1) break;
            ArgumentNode node = parameters.get(i);

            // Checks if the node is concatted
            if(node.isConcated()) {
                StringBuilder stringBuilder = new StringBuilder();
                for(int x = i; x < args.length; x++) {
                    if(args.length - 1 < x + nameArgs) continue;
                    stringBuilder.append(args[x + nameArgs]).append(" ");
                }
                objects.add(stringBuilder.substring(0, stringBuilder.toString().length() - 1));
                break;
            }

            String suppliedArgument = args[i + nameArgs];
            Object object = new ParamProcessor(node, suppliedArgument, sender).get();

            // If the object is returning null then that means there was a problem parsing
            if(object == null) return;
            objects.add(object);
        }

        if(args.length < requiredArgumentsLength()) {
            sendUsageMessage(sender);
            return;
        }

        int difference = (parameters.size() - requiredArgumentsLength()) - ((args.length - nameArgs) - requiredArgumentsLength());
        for(int i = 0; i < difference; i++) objects.add(null);

        if(async) {
            CommandHandler.executeAsync(() -> {
                try { method.invoke(parentClass, objects.toArray()); } catch(Exception exception) { exception.printStackTrace(); }
            });
            return;
        }

        method.invoke(parentClass, objects.toArray());
    }
}