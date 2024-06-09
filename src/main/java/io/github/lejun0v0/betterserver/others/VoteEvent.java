package io.github.lejun0v0.betterserver.others;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class VoteEvent {
    public static HashMap<VoteEvent, Long> events = new HashMap<>();
    private String event;
    private String description;
    private int status = STATUS_PENDING;
    public final static int STATUS_APPROVAL = 1;
    public final static int STATUS_REJECTION = 0;
    public final static int STATUS_TIE = -1;
    public final static int STATUS_PENDING = -2;
    public final static int STATUS_PREVIOUS = -3;
    private List<UUID> forList = new ArrayList<>();
    private List<UUID> againstList = new ArrayList<>();
    private long deadline;
    private CommandSender votingInitiator;
    public final static UUID CONSOLE_UUID = UUID.randomUUID();

    public VoteEvent(CommandSender votingInitiator, String event, long deadline, String description) {
        this.votingInitiator = votingInitiator;
        this.event = event;
        this.deadline = deadline;
        this.description = description;
    }

    public CommandSender getVotingInitiator() {
        return votingInitiator;
    }

    public void setVotingInitiator(CommandSender votingInitiator) {
        this.votingInitiator = votingInitiator;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<UUID> getForList() {
        return forList;
    }

    public List<UUID> getAgainstList() {
        return againstList;
    }


    public void close() {
        int foR = this.forList.size();
        int against = this.againstList.size();
        if (foR == against) {
            setStatus(VoteEvent.STATUS_TIE);
        } else {
            setStatus(foR > against ? VoteEvent.STATUS_APPROVAL : STATUS_REJECTION);
        }
    }

}
