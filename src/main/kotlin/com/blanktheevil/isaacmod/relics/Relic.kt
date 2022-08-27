package com.blanktheevil.isaacmod.relics

import basemod.abstracts.CustomRelic
import com.blanktheevil.isaacmod.utils.TextureLoaderKt
import com.megacrit.cardcrawl.helpers.GameDictionary
import com.megacrit.cardcrawl.helpers.PowerTip
import com.megacrit.cardcrawl.helpers.TipHelper
import com.megacrit.cardcrawl.relics.AbstractRelic
import java.util.*

abstract class Relic(
  relicId: String,
  texture: String,
  tier: RelicTier,
  sfx: LandingSound,
) : CustomRelic(relicId, "", tier, sfx) {
  init {
    img = TextureLoaderKt.getTexture("$texture.png")
    outlineImg = TextureLoaderKt.getTexture("$texture-outline.png")
    largeImg = TextureLoaderKt.getTexture("$texture.png")
    description = updatedDescription
  }

  override fun getUpdatedDescription(): String = DESCRIPTIONS[0]

  fun initializeRelicTips() {
    Scanner(description).apply {
      while (this.hasNext()) {
        var s = this.next()
        if (s[0] == '#') {
          s = s.substring(2)
        }
        s = s.replace(',', ' ')
          .replace('.', ' ')
          .trim()
          .toLowerCase()
        var alreadyExists = false
        if (GameDictionary.keywords.containsKey(s)) {
          s = GameDictionary.parentWord[s]
          for (tip in tips) {
            if (tip.header.toLowerCase() == s) {
              alreadyExists = true
              break
            }
          }
          if (alreadyExists) continue
          tips.add(PowerTip(TipHelper.capitalize(s), GameDictionary.keywords[s]))
        }
      }
      close()
    }
  }

  override fun makeCopy(): AbstractRelic = this::class.java.newInstance()
}