package framework.connector;

import framework.connector.payloads.*;

public enum ConnectionType {
    EXCESS_RESOURCE("EXCESS_RESOURCE", ExcessResourcePayload.class),
    STAT_CHANGE( "STAT_CHANGE", StatChangePayload.class),
    GLOBAL_EFFECT_CHANGE("GLOBAL_EFFECT_CHANGE", GlobalEffectChangePayload.class),
    CAST_CHANGE("CAST_CHANGE", CastChangePayload.class), //Replacements like
    TARGET_MODE("TARGET_MODE", TargetModePayload.class), //return the target viability of a skill's target
    CAN_PERFORM("CAN_PERFORM", CanPerformPayload.class),
    ON_PERFORM("ON_PERFORM", OnPerformPayload.class),
    IS_TARGETED("IS_TARGETED", IsTargetedPayload.class),
    EOT_HEAL_CHANGES("EOT_HEAL_CHANGES", HealChangesPayload.class),
    BASE_HEAL_CHANGES("BASE_HEAL_CHANGES", BaseHealChangesPayload.class), //Before defense calc
    BASE_DMG_CHANGES("BASE_DMG_CHANGES", BaseDmgChangesPayload.class), //Before defense calc
    CRITICAL_TRIGGER("CRITICAL_TRIGGER", CriticalTriggerPayload.class),
    DMG_CHANGES("DMG_CHANGES", DmgChangesPayload.class), //Dmg actually done, after def calc
    EFFECT_DMG_CHANGES("EFFECT_DMG_CHANGES", EffectDmgChangesPayload.class), //Dmg actually done, after def calc
    DMG_TRIGGER("DMG_TRIGGER", DmgTriggerPayload.class),
    EFFECT_DMG_TRIGGER("EFFECT_DMG_TRIGGER", DmgTriggerPayload.class),
    HEAL_CHANGES("HEAL_CHANGES", HealChangesPayload.class),
    EFFECT_FAILURE("EFFECT_FAILURE", EffectFailurePayload.class),
    EFFECT_ADDED("EFFECT_ADDED", EffectAddedPayload.class),
    ACTION_INFLICTION("ACTION_INFLICTION", ActionInflictionPayload.class),
    SHIELD_BROKEN("SHIELD_BROKEN", ShieldBrokenPayload.class),
    DMG_TO_SHIELD("DMG_TO_SHIELD", DmgToShieldPayload.class),
    SHIELD_CHANGES("SHIELD_CHANGES", ShieldChangesPayload.class),
    EQUIPMENT_CHANGE_TRIGGER("EQUIPMENT_CHANGE_TRIGGER", EquipmentChangePayload.class),
    MANA_REGAIN("MANA_REGAIN", ManaRegainPayload.class),
    UPDATE("UPDATE", UpdatePayload.class),
    START_OF_TURN("START_OF_TURN", StartOfTurnPayload.class),
    END_OF_ROUND("END_OF_TURN", EndOfRoundPayload.class),
    START_OF_MATCH("START_OF_MATCH", StartOfMatchPayload.class),
    DEATH_TRIGGER("DEATH_TRIGGER", DeathTriggerPayload.class);

    public String name;
    public Class<? extends ConnectionPayload> payloadClass;

    ConnectionType(String name, Class<? extends ConnectionPayload> clazz) {
        this.name = name;
        this.payloadClass = clazz;
    }
}
