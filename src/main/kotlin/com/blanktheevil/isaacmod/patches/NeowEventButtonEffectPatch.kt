package com.blanktheevil.isaacmod.patches

import basemod.ReflectionHacks
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn
import com.megacrit.cardcrawl.neow.NeowEvent
import com.megacrit.cardcrawl.neow.NeowReward

@SpirePatch2(clz = NeowEvent::class, method = "buttonEffect")
object NeowEventButtonEffectPatch {
  @JvmStatic
  @SpirePrefixPatch
  fun intercept(__instance: NeowEvent, buttonPressed: Int): SpireReturn<Void> {
    val rewards: ArrayList<NeowReward> = ReflectionHacks.getPrivate(__instance, NeowEvent::class.java, "rewards")
    val screenNum: Int = ReflectionHacks.getPrivate(__instance, NeowEvent::class.java, "screenNum")

    if (screenNum == 3 && buttonPressed > 3) {
      rewards[buttonPressed].activate()
      // TODO: Make Neow Talk
    }

    return SpireReturn.Continue()
  }
}