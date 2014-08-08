package com.asaskevich.smartcursor.utils;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class EntityPonter {
	static Minecraft mc = Minecraft.getMinecraft();
	public static Entity pointedEntity;
	final static double MODIFIER = 3.5D;

	public static void getEntityLookingAt(float par1) {
		if (mc.renderViewEntity != null) {
			if (mc.theWorld != null) {
				mc.pointedEntity = null;
				double d0 = /*(double) mc.playerController.getBlockReachDistance() * MODIFIER;*/ Setting.lookDistance;
				mc.objectMouseOver = mc.renderViewEntity.rayTrace(d0, par1);
				double d1 = d0;
				Vec3 vec3 = mc.renderViewEntity.getPosition(par1);
				// if (mc.playerController.extendedReach()) {
				// d0 = 6.0D * MODIFIER;
				// d1 = 6.0D * MODIFIER;
				// } else {
				// if (d0 > 3.0D * MODIFIER) {
				// d1 = 3.0D * MODIFIER;
				// }
				// d0 = d1;
				// }
				if (mc.objectMouseOver != null) {
					d1 = mc.objectMouseOver.hitVec.distanceTo(vec3);
				}
				Vec3 vec31 = mc.renderViewEntity.getLook(par1);
				Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
				pointedEntity = null;
				Vec3 vec33 = null;
				float f1 = 1.0F;
				List list = mc.theWorld.getEntitiesWithinAABBExcludingEntity(
						mc.renderViewEntity,
						mc.renderViewEntity.boundingBox.addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand(
								(double) f1, (double) f1, (double) f1));
				double d2 = d1;
				for (int i = 0; i < list.size(); ++i) {
					Entity entity = (Entity) list.get(i);
					float f2 = entity.getCollisionBorderSize();
					AxisAlignedBB axisalignedbb = entity.boundingBox.expand((double) f2, (double) f2, (double) f2);
					MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);
					if (axisalignedbb.isVecInside(vec3)) {
						if (0.0D < d2 || d2 == 0.0D) {
							pointedEntity = entity;
							vec33 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
							d2 = 0.0D;
						}
					} else if (movingobjectposition != null) {
						double d3 = vec3.distanceTo(movingobjectposition.hitVec);
						if (d3 < d2 || d2 == 0.0D) {
							if (entity == mc.renderViewEntity.ridingEntity && !entity.canRiderInteract()) {
								if (d2 == 0.0D) {
									pointedEntity = entity;
									vec33 = movingobjectposition.hitVec;
								}
							} else {
								pointedEntity = entity;
								vec33 = movingobjectposition.hitVec;
								d2 = d3;
							}
						}
					}
				}
				if (pointedEntity != null && (d2 < d1 || mc.objectMouseOver == null)) {
					mc.objectMouseOver = new MovingObjectPosition(pointedEntity, vec33);
					mc.pointedEntity = pointedEntity;
				}
			}
		}
	}
}
