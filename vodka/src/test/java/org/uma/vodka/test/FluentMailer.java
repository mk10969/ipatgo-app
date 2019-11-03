package org.uma.vodka.test;

import java.util.function.Consumer;

public class FluentMailer {
    private String from;
    private String to;
    private String subject;
    private String body;

    private FluentMailer() {
    }

    public FluentMailer setFrom(String from) {
        this.from = from;
        return this;
    }

    public FluentMailer setTo(String to) {
        this.to = to;
        return this;
    }

    public FluentMailer setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public FluentMailer setBody(String body) {
        this.body = body;
        return this;
    }

    @Override
    public String toString() {
        return "FluentMailer{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

    public static FluentMailer send(final Consumer<FluentMailer> consumer) {
        final FluentMailer mailer = new FluentMailer();
        // ローンパターン
        consumer.accept(mailer);
        return mailer;
    }

    public static FluentMailer create(){
        return FluentMailer.send(mailer->
                mailer.setFrom("a")
                        .setBody("b")
                        .setSubject("c")
                        .setTo("d")
        );

    }

}
