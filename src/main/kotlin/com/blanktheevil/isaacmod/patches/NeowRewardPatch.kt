package com.blanktheevil.isaacmod.patches

import com.blanktheevil.isaacmod.relics.PinkDrink
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.neow.NeowReward

@Suppress("unused")
@SpirePatch2(clz = NeowReward::class, method = "activate")
object NeowRewardPatch {

  @JvmStatic
  @SpirePrefixPatch
  fun intercept(__instance: NeowReward): SpireReturn<Void> {
    if (__instance.type == NeowRewardTypePatch.PINK_DRINK_REWARD) {
      AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH.div(2f), Settings.HEIGHT.div(2f), PinkDrink())
      return SpireReturn.Return(null)
    }


    return SpireReturn.Continue()
  }
}