package de.blocki.advancedrank.luckperms.listener;

import de.blocki.advancedrank.luckperms.commands.LP_rankCommandsTabComplete;
import de.blocki.advancedrank.main.Main;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.group.GroupCreateEvent;
import net.luckperms.api.event.group.GroupDeleteEvent;

public class GroupAddRemove {
    private final Main plugin;
    private final LuckPerms luckPerms;

    public GroupAddRemove(Main plugin, LuckPerms luckPerms) {
        this.plugin = plugin;
        this.luckPerms = luckPerms;
    }

    public void register() {
        EventBus eventBus = this.luckPerms.getEventBus();
        eventBus.subscribe(this.plugin, GroupCreateEvent.class, this::onGroupCreate);
        eventBus.subscribe(this.plugin, GroupDeleteEvent.class, this::onGroupDelete);
    }

    private void onGroupCreate(GroupCreateEvent event){ LP_rankCommandsTabComplete.groupsArg.add(event.getGroup().getName()); }

    private void onGroupDelete(GroupDeleteEvent event){ LP_rankCommandsTabComplete.groupsArg.remove(event.getGroupName()); }

}
