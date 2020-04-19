package org.uma.external.ipatgo;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class IPatGoExe {

    /**
     * TODO: ipatgo.exeのフルパス
     */
    private static final String ipatgoExe = "C:\\umagen\\ipatgo\\ipatgo.exe";

    public enum Mode {data, file, stat, history, deposit, withdraw}

    public enum TimeSeries {
        before("--before"),
        latest("--latest")
        ;
        private final String option;

        TimeSeries(String option) {
            this.option = option;
        }

        public String getOption() {
            return this.option;
        }
    }

    public static class CommandBuilder {
        private Mode mode;
        private final IPatGoProperties ipatGoProperties;
        private String argument;
        private String noSplash;
        private String timeSeries;

        public CommandBuilder(IPatGoProperties ipatGoProperties) {
            this.ipatGoProperties = ipatGoProperties;
        }

        public CommandBuilder setMode(Mode mode) {
            Objects.requireNonNull(mode);
            this.mode = mode;
            return this;
        }

        public CommandBuilder setArgument(String argument) {
            Objects.requireNonNull(argument);
            this.argument = argument;
            return this;
        }

        public CommandBuilder setNoSplash() {
            this.noSplash = "--nosplash";
            return this;
        }

        public CommandBuilder setTimeSeries(TimeSeries timeSeries) {
            Objects.requireNonNull(timeSeries);
            this.timeSeries = timeSeries.getOption();
            return this;
        }

        public List<String> build() {
            List<String> command = new ArrayList<>();
            command.add(ipatgoExe);
            command.add(mode.name());
            command.add(ipatGoProperties.getINetId());
            command.add(ipatGoProperties.getSubscriberNo());
            command.add(ipatGoProperties.getPassword());
            command.add(ipatGoProperties.getParsNo());
            command.add(argument);
            command.add(noSplash);
            command.add(timeSeries);
            command.removeIf(Objects::isNull);
            return command;
        }
    }

}
