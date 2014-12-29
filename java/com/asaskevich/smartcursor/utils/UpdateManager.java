package com.asaskevich.smartcursor.utils;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.URL;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import com.asaskevich.smartcursor.mod.ModInfo;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

public class UpdateManager {
	public PlayerEvent.PlayerLoggedInEvent	event;

	@SubscribeEvent
	public void onEnterWorld(PlayerEvent.PlayerLoggedInEvent event) {
		this.event = event;
		new Thread() {
			public void run() {
				try {
					String page = makeRequest("https://mods.io/mods/1089-smartcursor");
					int l = page.indexOf("<td><strong>");
					if (l < 0) throw new Exception("");
					int r = page.indexOf("</strong>", l + 10);
					if (r < 0) throw new Exception("");
					String localVersion = ModInfo.VERSION;
					String globalVersion = page.substring(l + 12, r);
					if (localVersion.compareToIgnoreCase(globalVersion) < 0) {
						UpdateManager.this.event.player.addChatComponentMessage(new ChatComponentText("SmartCursor is out-of-date. Your version - " + localVersion + ", latest version - " + globalVersion));
						UpdateManager.this.event.player.addChatComponentMessage(generateClickableMessage());
					}
				} catch (Exception e) {
					System.out.println("Unable to fetch information about updates!");
				}
			}
		}.start();
	}

	public static String makeRequest(String address) throws Exception {
		URL url;
		InputStream is;
		DataInputStream br;
		url = new URL(address);
		is = url.openStream();
		br = new DataInputStream(is);
		byte[] b = new byte[br.available()];
		for (int i = 0; i < b.length; i++) {
			b[i] = br.readByte();
		}
		is.close();
		return new String(b, "UTF-8");
	}

	public static IChatComponent generateClickableMessage() {
		ChatComponentText fileLink = new ChatComponentText("Update SmartCursor on mods.io!");
		fileLink.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://mods.io/mods/1089-smartcursor"));
		fileLink.getChatStyle().setUnderlined(true);
		return fileLink;
	}
}
