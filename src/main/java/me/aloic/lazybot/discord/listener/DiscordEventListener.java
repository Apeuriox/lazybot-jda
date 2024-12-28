package me.aloic.lazybot.discord.listener;

import jakarta.annotation.Resource;
import me.aloic.lazybot.discord.command.LazybotSlashCommand;
import me.aloic.lazybot.discord.command.registry.LazybotSlashCommandRegistry;
import me.aloic.lazybot.discord.util.ErrorResultHandler;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DiscordEventListener extends ListenerAdapter
{
    private static final Logger logger = LoggerFactory.getLogger(DiscordEventListener.class);

    @Resource
    private LazybotSlashCommandRegistry registry;

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;
        Message message = event.getMessage();
        String content = message.getContentRaw();
        logger.info("接受消息 [{}] : [{}]",event.getAuthor().getName(),event.getMessage().getContentRaw());

        if (content.equals("!ping"))
        {
            MessageChannel channel = event.getChannel();
            channel.sendMessage("Pong!").queue();
        }
    }
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event)
    {
        try {
            LazybotSlashCommand command = registry.getCommand(event.getName());
            if (command != null) {
                logger.info("正在处理 {} 命令",event.getName());
                command.execute(event);
            } else {
                event.reply("找不到对应指令").setEphemeral(true).queue();
            }
            } catch (Exception e) {
                ErrorResultHandler.createExceptionMessage(event, e);
            }

    }
}
