package com.blanktheevil.pinkdrink.patches

import basemod.ReflectionHacks
import com.blanktheevil.pinkdrink.makeID
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2
import com.evacipated.cardcrawl.modthespire.lib.SpirePatches2
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.events.AbstractEvent
import com.megacrit.cardcrawl.events.RoomEventDialog
import com.megacrit.cardcrawl.neow.NeowEvent
import com.megacrit.cardcrawl.neow.NeowReward


@Suppress("unused")
@SpirePatches2(
  SpirePatch2(clz = NeowEvent::class, method = "blessing"),
  SpirePatch2(clz = NeowEvent::class, method = "miniBlessing"),
)
object NeowEventPatch {
  @JvmStatic
  @SpirePostfixPatch
  fun intercept(__instance: NeowEvent) {
    val rewards: ArrayList<NeowReward> = ReflectionHacks.getPrivate(__instance, NeowEvent::class.java, "rewards")
    val roomEventText: RoomEventDialog = ReflectionHacks.getPrivate(__instance, AbstractEvent::class.java, "roomEventText")
    rewards.add(
      NeowReward(false).apply {
        type = NeowRewardTypePatch.PINK_DRINK_REWARD
        optionLabel = CardCrawlGame.languagePack.getUIString("PinkDrinkOption".makeID()).TEXT[0]
      }
    )

    roomEventText.addDialogOption(rewards[rewards.lastIndex].optionLabel)
  }
}
