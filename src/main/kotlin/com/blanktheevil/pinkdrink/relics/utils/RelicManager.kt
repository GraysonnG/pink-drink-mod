package com.blanktheevil.pinkdrink.relics.utils

import basemod.AutoAdd
import com.blanktheevil.pinkdrink.PinkDrinkMod
import com.blanktheevil.pinkdrink.relics.Relic
import com.megacrit.cardcrawl.helpers.RelicLibrary
import com.megacrit.cardcrawl.unlock.UnlockTracker

@AutoAdd.Ignore
object RelicManager {
  fun addRelics() {
    AutoAdd(PinkDrinkMod.properties.modid)
      .packageFilter(Relic::class.java)
      .filter(AutoAdd.NotPackageFilter(RelicManager::class.java))
      .any(Relic::class.java) { info, relic ->
        RelicLibrary.add(relic)
        if (info.seen) {
          UnlockTracker.markRelicAsSeen(relic.relicId)
        }
      }
  }
}