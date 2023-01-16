package com.chrono;

import lombok.Getter;
import net.runelite.api.Prayer;
import net.runelite.api.Quest;
import net.runelite.api.Skill;
import net.runelite.api.widgets.WidgetInfo;

import java.util.*;

public enum Release {
    JANUARY_2001(
        "January 2001",
        new GregorianCalendar(2001, Calendar.JANUARY, 31).getTime(),
        Arrays.asList(12337, 12338, 12339, 12340, 12341, 12342, 12593, 12594, 12595, 12596, 12597, 12598, 12849, 12850, 12851, 12852, 12853, 12854, 13108, 13109, 13110),
        Arrays.asList(Skill.HITPOINTS, Skill.ATTACK, Skill.STRENGTH, Skill.DEFENCE, Skill.RANGED, Skill.PRAYER, Skill.MAGIC, Skill.MINING, Skill.SMITHING, Skill.COOKING, Skill.FIREMAKING, Skill.WOODCUTTING, Skill.CRAFTING),
        null,
        Arrays.asList(Quest.COOKS_ASSISTANT, Quest.DEMON_SLAYER, Quest.THE_RESTLESS_GHOST, Quest.ROMEO__JULIET, Quest.SHIELD_OF_ARRAV, Quest.SHEEP_SHEARER,
                Quest.ERNEST_THE_CHICKEN, Quest.VAMPYRE_SLAYER
        )
    ),
    FEBRUARY_2001(
            "February 2001",
            new GregorianCalendar(2001, Calendar.FEBRUARY, 28).getTime(),
            Arrays.asList(10806),
            null,
            null,
            Arrays.asList(Quest.IMP_CATCHER, Quest.PRINCE_ALI_RESCUE)
    ),
    MARCH_2001(
            "March 2001",
            new GregorianCalendar(2001, Calendar.MARCH, 31).getTime(),
            null,
            null,
            null,
            Arrays.asList(Quest.THE_GARDEN_OF_DEATH)
    ),
    APRIL_2001(
            "April 2001",
            new GregorianCalendar(2001, Calendar.APRIL, 30).getTime(),
            null,
            null,
            null,
            null
    ),
    MAY_2001(
            "May 2001",
            new GregorianCalendar(2001, Calendar.MAY, 31).getTime(),
            null,
            null,
            Arrays.asList(Prayer.THICK_SKIN, Prayer.BURST_OF_STRENGTH, Prayer.CLARITY_OF_THOUGHT, Prayer.ROCK_SKIN,
                    Prayer.SUPERHUMAN_STRENGTH, Prayer.IMPROVED_REFLEXES, Prayer.RAPID_HEAL, Prayer.RAPID_RESTORE,
                    Prayer.PROTECT_ITEM, Prayer.STEEL_SKIN, Prayer.ULTIMATE_STRENGTH, Prayer.INCREDIBLE_REFLEXES,
                    Prayer.PROTECT_FROM_MELEE, Prayer.PROTECT_FROM_MISSILES
            ),
            Arrays.asList(Quest.GOBLIN_DIPLOMACY)
    ),
    JUNE_2001(
            "June 2001",
            new GregorianCalendar(2001, Calendar.JUNE, 30).getTime(),
            null,
            null,
            null,
            Arrays.asList(Quest.PIRATES_TREASURE)
    ),
    JULY_2001(
            "July 2001",
            new GregorianCalendar(2001, Calendar.JULY, 31).getTime(),
            null,
            null,
            null,
            null
    ),
    AUGUST_2001(
            "August 2001",
            new GregorianCalendar(2001, Calendar.AUGUST, 31).getTime(),
            null,
            null,
            null,
            null
    ),
    SEPTEMBER_2001(
            "September 2001",
            new GregorianCalendar(2001, Calendar.SEPTEMBER, 30).getTime(),
            null,
            null,
            null,
            Arrays.asList(Quest.DRAGON_SLAYER_I)
    ),
    OCTOBER_2001(
            "October 2001",
            new GregorianCalendar(2001, Calendar.OCTOBER, 31).getTime(),
            null,
            null,
            null,
            null
    ),
    NOVEMBER_2001(
            "November 2001",
            new GregorianCalendar(2001, Calendar.NOVEMBER, 30).getTime(),
            null,
            null,
            null,
            null
    ),
    DECEMBER_2001(
            "December 2001",
            new GregorianCalendar(2001, Calendar.DECEMBER, 31).getTime(),
            null,
            null,
            null,
            null
    );

    @Getter
    private String name;
    @Getter
    private Date date;
    @Getter
    private List<Integer> regions;
    @Getter
    private List<Skill> skills;
    @Getter
    private List<Prayer> prayers;
    @Getter
    private List<Quest> quests;

    Release(String name, Date date, List<Integer> regions, List<Skill> skills, List<Prayer> prayers, List<Quest> quests) {
        this.name = name;
        this.date = date;
        this.skills = skills;
        this.regions = regions;
        this.prayers = prayers;
        this.quests = quests;
    }

    public static List<Integer> getRegions(Release release) {
        Release[] releases = Release.values();
        List<Integer> regions = new ArrayList<>();

        for(Release r : releases) {
            if(r.regions != null) regions.addAll(r.getRegions());
            if(r.equals(release)) break;
        }

        return regions;
    }

    public static List<Skill> getSkills(Release release) {
        Release[] releases = Release.values();
        List<Skill> skills = new ArrayList<>();

        for(Release r : releases) {
            if(r.skills != null) skills.addAll(r.getSkills());
            if(r.equals(release)) break;
        }

        return skills;
    }

    public static List<Prayer> getPrayers(Release release) {
        Release[] releases = Release.values();
        List<Prayer> prayers = new ArrayList<>();

        for(Release r : releases) {
            if(r.prayers != null) prayers.addAll(r.getPrayers());
            if(r.equals(release)) break;
        }

        return prayers;
    }

    public static List<Quest> getQuests(Release release) {
        Release[] releases = Release.values();
        List<Quest> quests = new ArrayList<>();

        for(Release r : releases) {
            if(r.quests != null) quests.addAll(r.getQuests());
            if(r.equals(release)) break;
        }

        return quests;
    }
}
