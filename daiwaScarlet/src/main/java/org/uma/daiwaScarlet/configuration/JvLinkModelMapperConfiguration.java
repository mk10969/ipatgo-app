package org.uma.daiwaScarlet.configuration;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.uma.daiwaScarlet.code.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Configuration
public class JvLinkModelMapperConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(toLocalDate);
        modelMapper.addConverter(toRaceCourseCode);
        modelMapper.addConverter(toWeekDayCode);
        modelMapper.addConverter(toRaceGradeCode);
        modelMapper.addConverter(toJockeyApprenticeCode);
        return modelMapper;
    }

    private Converter<String, LocalDate> toLocalDate = new AbstractConverter<String, LocalDate>() {
        @Override
        protected LocalDate convert(String source) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
            return LocalDate.parse(source, format);
        }
    };

    private Converter<String, RaceCourseCode> toRaceCourseCode = new AbstractConverter<String, RaceCourseCode>() {
        @Override
        protected RaceCourseCode convert(String source) {
            return RaceCourseCode.of(source);
        }
    };

    private Converter<String, WeekDayCode> toWeekDayCode = new AbstractConverter<String, WeekDayCode>() {
        @Override
        protected WeekDayCode convert(String source) {
            return WeekDayCode.of(source);
        }
    };

    private Converter<String, RaceGradeCode> toRaceGradeCode = new AbstractConverter<String, RaceGradeCode>() {
        @Override
        protected RaceGradeCode convert(String source) {
            return RaceGradeCode.of(source);
        }
    };

    private Converter<String, JockeyApprenticeCode> toJockeyApprenticeCode = new AbstractConverter<String, JockeyApprenticeCode>() {
        @Override
        protected JockeyApprenticeCode convert(String source) {
            Integer code = Integer.valueOf(source);
            return JockeyApprenticeCode.of(code);
        }
    };

    private Converter<String, AbnormalDivisionCode> toAbnormalDivisionCode = new AbstractConverter<String, AbnormalDivisionCode>() {
        @Override
        protected AbnormalDivisionCode convert(String source) {
            Integer code = Integer.valueOf(source);
            return AbnormalDivisionCode.of(code);
        }
    };

    private Converter<String, BredCode> toBredCode = new AbstractConverter<String, BredCode>() {
        @Override
        protected BredCode convert(String source) {
            Integer code = Integer.valueOf(source);
            return BredCode.of(code);
        }
    };

    private Converter<String, EastOrWestBelongCode> toEastOrWestBelongCode = new AbstractConverter<String, EastOrWestBelongCode>() {
        @Override
        protected EastOrWestBelongCode convert(String source) {
            Integer code = Integer.valueOf(source);
            return EastOrWestBelongCode.of(code);
        }
    };


}
