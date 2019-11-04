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

    private static final Converter<String, LocalDate> toLocalDate = new AbstractConverter<String, LocalDate>() {
        @Override
        protected LocalDate convert(String source) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
            return LocalDate.parse(source, format);
        }
    };

    private static final Converter<String, RaceCourseCode> toRaceCourseCode = new AbstractConverter<String, RaceCourseCode>() {
        @Override
        protected RaceCourseCode convert(String source) {
            return RaceCourseCode.of(source);
        }
    };

    private static final Converter<String, WeekDayCode> toWeekDayCode = new AbstractConverter<String, WeekDayCode>() {
        @Override
        protected WeekDayCode convert(String source) {
            Integer code = Integer.valueOf(source);
            return WeekDayCode.of(code);
        }
    };

    private static final Converter<String, RaceGradeCode> toRaceGradeCode = new AbstractConverter<String, RaceGradeCode>() {
        @Override
        protected RaceGradeCode convert(String source) {
            return RaceGradeCode.of(source);
        }
    };

    private static final Converter<String, JockeyApprenticeCode> toJockeyApprenticeCode = new AbstractConverter<String, JockeyApprenticeCode>() {
        @Override
        protected JockeyApprenticeCode convert(String source) {
            Integer code = Integer.valueOf(source);
            return JockeyApprenticeCode.of(code);
        }
    };

    private static final Converter<String, AbnormalDivisionCode> toAbnormalDivisionCode = new AbstractConverter<String, AbnormalDivisionCode>() {
        @Override
        protected AbnormalDivisionCode convert(String source) {
            Integer code = Integer.valueOf(source);
            return AbnormalDivisionCode.of(code);
        }
    };

    private static final Converter<String, BredCode> toBredCode = new AbstractConverter<String, BredCode>() {
        @Override
        protected BredCode convert(String source) {
            Integer code = Integer.valueOf(source);
            return BredCode.of(code);
        }
    };

    private static final Converter<String, EastOrWestBelongCode> toEastOrWestBelongCode = new AbstractConverter<String, EastOrWestBelongCode>() {
        @Override
        protected EastOrWestBelongCode convert(String source) {
            Integer code = Integer.valueOf(source);
            return EastOrWestBelongCode.of(code);
        }
    };

    private static final Converter<String, RaceSignCode> toRaceSignCode = new AbstractConverter<String, RaceSignCode>() {
        @Override
        protected RaceSignCode convert(String source) {
            return RaceSignCode.of(source);
        }
    };

    private static final Converter<String, SexCode> toSexCode = new AbstractConverter<String, SexCode>() {
        @Override
        protected SexCode convert(String source) {
            Integer code = Integer.valueOf(source);
            return SexCode.of(code);
        }
    };

    private static final Converter<String, MarginCode> toMarginCode = new AbstractConverter<String, MarginCode>() {
        @Override
        protected MarginCode convert(String source) {
            return MarginCode.of(source);
        }
    };

    private static final Converter<String, HorseSignCode> toHorseSignCode = new AbstractConverter<String, HorseSignCode>() {
        @Override
        protected HorseSignCode convert(String source) {
            return HorseSignCode.of(source);
        }
    };

    private static final Converter<String, TurfOrDirtConditionCode> toTurfOrDirtConditionCode = new AbstractConverter<String, TurfOrDirtConditionCode>() {
        @Override
        protected TurfOrDirtConditionCode convert(String source) {
            Integer code = Integer.valueOf(source);
            return TurfOrDirtConditionCode.of(code);
        }
    };

    private static final Converter<String, WeatherCode> toWeatherCode = new AbstractConverter<String, WeatherCode>() {
        @Override
        protected WeatherCode convert(String source) {
            Integer code = Integer.valueOf(source);
            return WeatherCode.of(code);
        }
    };

    private static final Converter<String, WeightTypeCode> toWeightTypeCode = new AbstractConverter<String, WeightTypeCode>() {
        @Override
        protected WeightTypeCode convert(String source) {
            Integer code = Integer.valueOf(source);
            return WeightTypeCode.of(code);
        }
    };

    private static final Converter<String, TrackCode> toTrackCode = new AbstractConverter<String, TrackCode>() {
        @Override
        protected TrackCode convert(String source) {
            Integer code = Integer.valueOf(source);
            return TrackCode.of(code);
        }
    };

    private static final Converter<String, JockeyLicenseCode> toJockeyLicenseCode = new AbstractConverter<String, JockeyLicenseCode>() {
        @Override
        protected JockeyLicenseCode convert(String source) {
            Integer code = Integer.valueOf(source);
            return JockeyLicenseCode.of(code);
        }
    };

    private static final Converter<String, RaceTypeCode> toRaceTypeCode = new AbstractConverter<String, RaceTypeCode>() {
        @Override
        protected RaceTypeCode convert(String source) {
            Integer code = Integer.valueOf(source);
            return RaceTypeCode.of(code);
        }
    };

    private static final Converter<String, HairColorCode> toHairColorCode = new AbstractConverter<String, HairColorCode>() {
        @Override
        protected HairColorCode convert(String source) {
            return HairColorCode.of(source);
        }
    };


    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(toLocalDate);
        modelMapper.addConverter(toRaceCourseCode);
        modelMapper.addConverter(toWeekDayCode);
        modelMapper.addConverter(toRaceGradeCode);
        modelMapper.addConverter(toJockeyApprenticeCode);
        modelMapper.addConverter(toAbnormalDivisionCode);
        modelMapper.addConverter(toBredCode);
        modelMapper.addConverter(toEastOrWestBelongCode);
        modelMapper.addConverter(toRaceSignCode);
        modelMapper.addConverter(toSexCode);
        modelMapper.addConverter(toMarginCode);
        modelMapper.addConverter(toHorseSignCode);
        modelMapper.addConverter(toTurfOrDirtConditionCode);
        modelMapper.addConverter(toWeatherCode);
        modelMapper.addConverter(toWeightTypeCode);
        modelMapper.addConverter(toTrackCode);
        modelMapper.addConverter(toJockeyLicenseCode);
        modelMapper.addConverter(toRaceTypeCode);
        modelMapper.addConverter(toHairColorCode);
        return modelMapper;
    }

}
