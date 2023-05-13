package com.mactso.harderfarther.network;

import java.util.function.Supplier;

import com.mactso.harderfarther.client.GrimSongManager;
import net.minecraft.network.PacketByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class GrimClientSongPacket {
	private int song;
	
	public GrimClientSongPacket (int song )
	{
		this.song = song;
	}
	
	public static void processPacket(GrimClientSongPacket message, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork( () -> 
			{
				GrimSongManager.startSong(message.song);
			}
		);
		ctx.get().setPacketHandled(true);
	}
	
	public static GrimClientSongPacket readPacketData(PacketByteBuf buf)
	{
		int song = buf.readInt();
		return new GrimClientSongPacket(song);
	}
	
	public static void writePacketData(GrimClientSongPacket msg, PacketByteBuf buf)
	{
		msg.encode(buf);
	}
	
	public void encode(PacketByteBuf buf)
	{
			
		buf.writeInt(this.song);

	}
}
