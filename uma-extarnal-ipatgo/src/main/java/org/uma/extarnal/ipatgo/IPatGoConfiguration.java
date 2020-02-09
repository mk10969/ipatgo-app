package org.uma.extarnal.ipatgo;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class IPatGoConfiguration {

    /**
     * TODO: ipatgo.exeのフルパス
     */
    private static final String ipatgoExe = "ipatgo.exe";

    public enum Mode {data, file, stat, history, deposit, withdraw}

    public enum TimeSeries {
        before("--before"),
        latest("--latest")
        ;
        private String arg;

        TimeSeries(String arg) {
            this.arg = arg;
        }

        public String getArg() {
            return this.arg;
        }
    }

    public static class Builder {
        private Mode mode;
        private final IPatGoProperties ipatGoProperties;
        private String argument;
        private String noSplash;
        private String timeSeries;

        public Builder(IPatGoProperties ipatGoProperties) {
            this.ipatGoProperties = ipatGoProperties;
        }

        public Builder setMode(Mode mode) {
            Objects.requireNonNull(mode);
            this.mode = mode;
            return this;
        }

        public Builder setArgument(String argument) {
            Objects.requireNonNull(argument);
            this.argument = argument;
            return this;
        }

        public Builder setNoSplash() {
            this.noSplash = "--nosplash";
            return this;
        }

        public Builder setTimeSeries(TimeSeries timeSeries) {
            Objects.requireNonNull(timeSeries);
            this.timeSeries = timeSeries.getArg();
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
