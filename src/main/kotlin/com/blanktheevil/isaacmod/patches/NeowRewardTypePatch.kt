package com.blanktheevil.isaacmod.patches

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum
import com.megacrit.cardcrawl.neow.NeowReward

object NeowRewardTypePatch {
  @SpireEnum
  lateinit var PINK_DRINK_REWARD: NeowReward.NeowRewardType
}