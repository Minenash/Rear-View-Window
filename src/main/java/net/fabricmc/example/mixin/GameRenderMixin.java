package net.fabricmc.example.mixin;

import net.fabricmc.example.ExampleMod;
import net.fabricmc.example.RearViewBreakout;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.util.ScreenshotUtils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GameRenderer.class)
public abstract class GameRenderMixin {

    @Shadow @Final private MinecraftClient client;

    @Shadow private boolean renderHand;

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;renderWorld(FJLnet/minecraft/client/util/math/MatrixStack;)V"))
    private void getRender(GameRenderer gameRenderer, float tickDelta, long limitTime, MatrixStack matrix) {

        if (ExampleMod.isRearViewOpen) {
            Entity entity = client.getCameraEntity();

            entity.yaw += 180;
            renderHand = false;

            gameRenderer.renderWorld(tickDelta, limitTime, new MatrixStack());

            NativeImage image = ScreenshotUtils.takeScreenshot(0, 0, client.getFramebuffer());
            RearViewBreakout.imageRatio = image.getWidth() / (double) image.getHeight();
            client.getTextureManager().registerTexture(new Identifier("rearview", "image"), new NativeImageBackedTexture(image));

            entity.yaw -= 180;
            renderHand = true;
        }

        gameRenderer.renderWorld(tickDelta, limitTime, matrix);

    }


}
