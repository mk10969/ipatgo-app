package org.uma.platform.feed.application.configuration;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.uma.platform.common.code.*;
import org.uma.platform.common.config.spec.RecordSpec;
import org.uma.platform.common.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.EnumMap;


@Configuration
public class JvLinkModelMapperConfiguration {

    private static final Converter<String, LocalDate> toLocalDate = new AbstractConverter<String, LocalDate>() {
        @Override
        protected LocalDate convert(String source) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
            // 日付が設定されていない場合のデフォルト値
            if ("00000000".equals(source)){
                // 一番古い日付を設定しておく。
                return LocalDate.MIN;
            }
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

    private static final Converter<String, BreedCode> toBredCode = new AbstractConverter<String, BreedCode>() {
        @Override
        protected BreedCode convert(String source) {
            Integer code = Integer.valueOf(source);
            return BreedCode.of(code);
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
    public EnumMap<RecordSpec, Class<?>> recordSpecPairEnumMap() {
        EnumMap<RecordSpec, Class<?>> enumMap = new EnumMap<>(RecordSpec.class);
        // RACE
        enumMap.put(RecordSpec.RA, RacingDetails.class);
        enumMap.put(RecordSpec.SE, HorseRacingDetails.class);
        enumMap.put(RecordSpec.HR, RaceRefund.class);
        enumMap.put(RecordSpec.H1, VoteCount.class);

        // BLOD
        enumMap.put(RecordSpec.SK, Offspring.class);
        enumMap.put(RecordSpec.BT, Ancestry.class);
        enumMap.put(RecordSpec.HN, BreedingHorse.class);

        enumMap.put(RecordSpec.CS, Course.class);
        enumMap.put(RecordSpec.UM, RaceHorse.class);
        enumMap.put(RecordSpec.KS, Jockey.class);
        enumMap.put(RecordSpec.CH, Trainer.class);
        enumMap.put(RecordSpec.BR, Breeder.class);
        enumMap.put(RecordSpec.BN, Owner.class);
        return enumMap;
    }

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
