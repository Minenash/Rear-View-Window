package net.fabricmc.example;

import com.raphydaphy.breakoutapi.breakout.Breakout;
import com.raphydaphy.breakoutapi.breakout.window.BreakoutWindow;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;


public class RearViewBreakout extends Breakout {

    public static double imageRatio = 1;

    public RearViewBreakout() {
        super(ExampleMod.REAR_VIEW_BREAKOUT, new BreakoutWindow("Rear View", 720, 480));
    }

    @Override
    public void render() {
        super.render();

        client.getTextureManager().bindTexture(new Identifier("rearview","image"));


        int w = (int)(imageRatio*window.getHeight());

        DrawableHelper.drawTexture(new MatrixStack(), window.getWidth()/2 - w/2, 0, 0.0F, 0.0F, w, window.getHeight(), w, window.getHeight());

    }
}
