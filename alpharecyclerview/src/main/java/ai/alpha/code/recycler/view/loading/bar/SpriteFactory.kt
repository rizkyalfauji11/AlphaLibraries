package ai.alpha.code.recycler.view.loading.bar

import ai.alpha.code.recycler.view.loading.Sprite
import ai.alpha.code.recycler.view.loading.ThreeBounce

class SpriteFactory {
    companion object {
        fun create(): Sprite {
            return ThreeBounce()
        }
    }
}