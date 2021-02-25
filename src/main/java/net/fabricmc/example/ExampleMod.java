package net.fabricmc.example;

import com.raphydaphy.breakoutapi.BreakoutAPIClient;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class ExampleMod implements ModInitializer {

	public static final Identifier REAR_VIEW_BREAKOUT = new Identifier("rearview", "breakout");
	public static boolean isRearViewOpen = false;

	public static final KeyBinding TOGGLE_BREAKOUT = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"Toggle RearView",
			InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G,
			"key.categories.misc"));

	@Override
	public void onInitialize() {
		ClientTickEvents.END_CLIENT_TICK.register(client -> {

			while (TOGGLE_BREAKOUT.wasPressed()) {
				boolean isOpen = BreakoutAPIClient.getBreakouts().containsKey(REAR_VIEW_BREAKOUT);
				if (isOpen)
					BreakoutAPIClient.closeBreakout(REAR_VIEW_BREAKOUT);
				else
					BreakoutAPIClient.openBreakout(new RearViewBreakout());
				isRearViewOpen = !isOpen;
			}
		});
	}
}
