package com.blanktheevil.isaacmod.relics.utils

import basemod.AutoAdd
import com.blanktheevil.isaacmod.IsaacMod
import com.blanktheevil.isaacmod.relics.Relic
import com.megacrit.cardcrawl.helpers.RelicLibrary
import com.megacrit.cardcrawl.unlock.UnlockTracker

@AutoAdd.Ignore
object RelicManager {
  fun addRelics() {
    AutoAdd(IsaacMod.properties.modid)
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