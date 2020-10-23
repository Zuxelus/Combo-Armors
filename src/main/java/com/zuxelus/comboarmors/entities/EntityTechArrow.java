package com.zuxelus.comboarmors.entities;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityTechArrow extends EntityArrow implements IProjectile
{
	private int xTile = -1;
	private int yTile = -1;
	private int zTile = -1;
	private Block inTile;
	private int inData;
	private boolean inGround;

	/** 1 if the player can pick up the arrow */
	public int canBePickedUp;

	/** Seems to be some sort of timer for animating an arrow. */
	public int arrowShake;

	/** The owner of this arrow. */
	public Entity shootingEntity;
	private int ticksInGround;
	private int ticksInAir;
	private double damage = 2.0D;

	public boolean explosive = false;

	/** The amount of knockback an arrow applies when it hits a mob. */
	private int knockbackStrength;

    public EntityTechArrow(World world)
    {
        super(world);
        this.renderDistanceWeight = 10.0D;
        this.setSize(0.5F, 0.5F);
    }
	
	public EntityTechArrow(World world, double par2, double par4, double par6)
	{
		super(world, par2, par4, par6);
		this.renderDistanceWeight = 10.0D;
		this.setSize(0.5F, 0.5F);
		this.setPosition(par2, par4, par6);
		this.yOffset = 0.0F;
	}

	public EntityTechArrow(World world, EntityLivingBase par2EntityLiving, float par3)
	{
		super(world, par2EntityLiving, par3);
		this.renderDistanceWeight = 10.0D;
		this.shootingEntity = par2EntityLiving;
		if (par2EntityLiving instanceof EntityPlayer) 
		{
			this.canBePickedUp = 1;
		}
		this.setSize(0.5F, 0.5F);
		this.setLocationAndAngles(par2EntityLiving.posX, par2EntityLiving.posY + (double)par2EntityLiving.getEyeHeight(), par2EntityLiving.posZ, par2EntityLiving.rotationYaw, par2EntityLiving.rotationPitch);
		this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
		this.posY -= 0.1000000014901162D;
		this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
		this.setPosition(this.posX, this.posY, this.posZ);
		this.yOffset = 0.0F;
		this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
		this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
		this.motionY = (double)(-MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI));
		this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, par3 * 1.5F, 1.0F);
	}

	public boolean canAttackWithItem()
	{
		return false;
	}

	protected boolean canTriggerWalking()
	{
		return false;
	}

	@Override
	protected void entityInit()
	{
		this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
	}

	public double getDamage()
	{
		return this.damage;
	}

	public boolean getExplosive()
	{
		return this.explosive;
	}

	public boolean getIsCritical()
	{
		byte var1 = this.dataWatcher.getWatchableObjectByte(16);
		return (var1 & 1) != 0;
	}

	@SideOnly(Side.CLIENT)
	public float getShadowSize()
	{
		return 0.0F;
	}

	public void onCollideWithPlayer(EntityPlayer par1EntityPlayer)
	{
		if (!this.worldObj.isRemote && this.inGround && this.arrowShake <= 0)
		{
			boolean flag = this.canBePickedUp == 1 || this.canBePickedUp == 2 && par1EntityPlayer.capabilities.isCreativeMode;

			if (this.canBePickedUp == 1 && !par1EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.arrow, 1)))
			{
				flag = false;
			}

			if (flag)
			{
				this.playSound("random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
				par1EntityPlayer.onItemPickup(this, 1);
				this.setDead();
			}
		}
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();
		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
		{
			float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f) * 180.0D / Math.PI);
		}
		Block block = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);
		if (block.getMaterial() != Material.air)
		{
			block.setBlockBoundsBasedOnState(this.worldObj, this.xTile, this.yTile, this.zTile);
			AxisAlignedBB axisalignedbb = block.getCollisionBoundingBoxFromPool(this.worldObj, this.xTile, this.yTile, this.zTile);
			if (axisalignedbb != null && axisalignedbb.isVecInside(Vec3.createVectorHelper(this.posX, this.posY, this.posZ)))
			{
				this.inGround = true;
			}
		}
		if (this.arrowShake > 0) 
		{
			--this.arrowShake;
		}
		if (this.inGround)
		{
			int j = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);

			if (block == this.inTile && j == this.inData)
			{
				++this.ticksInGround;

				if (this.ticksInGround == 1200) 
				{
					this.setDead();
				}
			}
			else
			{
				this.inGround = false;
				this.motionX *= (double)(this.rand.nextFloat() * 0.2F);
				this.motionY *= (double)(this.rand.nextFloat() * 0.2F);
				this.motionZ *= (double)(this.rand.nextFloat() * 0.2F);
				this.ticksInGround = 0;
				this.ticksInAir = 0;
			}
		}
		else
		{
			++this.ticksInAir;
			Vec3 vec31 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
			Vec3 vec3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			MovingObjectPosition movingobjectposition = this.worldObj.func_147447_a(vec31, vec3, false, true, false);
			vec31 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
			vec3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			if (movingobjectposition != null) 
			{
				vec3 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
			}
			Entity entity = null;
			List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
			double d0 = 0.0D;
			int i;
			float f1;

			for (i = 0; i < list.size(); ++i)
			{
				Entity entity1 = (Entity)list.get(i);

				if (entity1.canBeCollidedWith() && (entity1 != this.shootingEntity || this.ticksInAir >= 5))
				{
					f1 = 0.3F;
					AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand((double)f1, (double)f1, (double)f1);
					MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec31, vec3);

					if (movingobjectposition1 != null)
					{
						double d1 = vec31.distanceTo(movingobjectposition1.hitVec);

						if (d1 < d0 || d0 == 0.0D)
						{
							entity = entity1;
							d0 = d1;
						}
					}
				}
			}
			if (entity != null) 
			{
				movingobjectposition = new MovingObjectPosition(entity);
			}

			if (movingobjectposition != null && movingobjectposition.entityHit != null && movingobjectposition.entityHit instanceof EntityPlayer)
			{
				EntityPlayer entityplayer = (EntityPlayer)movingobjectposition.entityHit;

				if (entityplayer.capabilities.disableDamage || this.shootingEntity instanceof EntityPlayer && !((EntityPlayer)this.shootingEntity).canAttackPlayer(entityplayer))
				{
					movingobjectposition = null;
				}
			}

			float f2;
			float f4;

			if (movingobjectposition != null) 
			{
				if (movingobjectposition.entityHit != null)
				{
					f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
					int k = MathHelper.ceiling_double_int((double)f2 * this.damage);

					if (this.getIsCritical()) 
					{
						k += this.rand.nextInt(k / 2 + 2);
					}

					DamageSource damagesource = null;

					if (this.shootingEntity == null) 
					{
						damagesource = DamageSource.causeArrowDamage(this, this);
					}
					else
					{
						damagesource = DamageSource.causeArrowDamage(this, this.shootingEntity);
					}
					if (this.isBurning() && !(movingobjectposition.entityHit instanceof EntityEnderman))
					{
						movingobjectposition.entityHit.setFire(5);
					}

					if (movingobjectposition.entityHit.attackEntityFrom(damagesource, (float)k))
					{
						if (movingobjectposition.entityHit instanceof EntityLivingBase)
						{
							EntityLivingBase entitylivingbase = (EntityLivingBase)movingobjectposition.entityHit;
							if (!this.worldObj.isRemote) 
							{
								entitylivingbase.setArrowCountInEntity(entitylivingbase.getArrowCountInEntity() + 1);
							}

							if (this.knockbackStrength > 0)
							{
								f4 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);

								if (f4 > 0.0F)
								{
									movingobjectposition.entityHit.addVelocity(this.motionX * (double)this.knockbackStrength * 0.6000000238418579D / (double)f4, 0.1D, this.motionZ * (double)this.knockbackStrength * 0.6000000238418579D / (double)f4);
								}
							}

							if (this.shootingEntity != null && this.shootingEntity instanceof EntityLivingBase)
							{
								EnchantmentHelper.func_151384_a(entitylivingbase, this.shootingEntity);
								EnchantmentHelper.func_151385_b((EntityLivingBase)this.shootingEntity, entitylivingbase);
							}

							if (this.shootingEntity != null && movingobjectposition.entityHit != this.shootingEntity && movingobjectposition.entityHit instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP)
							{
								((EntityPlayerMP)this.shootingEntity).playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(6, 0.0F));
							}
						}
						this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
						if (!(movingobjectposition.entityHit instanceof EntityEnderman)) 
						{
							this.setDead();
						}
					}
					else
					{
						this.motionX *= -0.10000000149011612D;
						this.motionY *= -0.10000000149011612D;
						this.motionZ *= -0.10000000149011612D;
						this.rotationYaw += 180.0F;
						this.prevRotationYaw += 180.0F;
						this.ticksInAir = 0;
					}
				}
				else
				{
					this.xTile = movingobjectposition.blockX;
					this.yTile = movingobjectposition.blockY;
					this.zTile = movingobjectposition.blockZ;
					this.inTile = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);
					this.inData = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
					this.motionX = (double)((float)(movingobjectposition.hitVec.xCoord - this.posX));
					this.motionY = (double)((float)(movingobjectposition.hitVec.yCoord - this.posY));
					this.motionZ = (double)((float)(movingobjectposition.hitVec.zCoord - this.posZ));
					f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
					this.posX -= this.motionX / (double)f2 * 0.05000000074505806D;
					this.posY -= this.motionY / (double)f2 * 0.05000000074505806D;
					this.posZ -= this.motionZ / (double)f2 * 0.05000000074505806D;
					this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
					this.inGround = true;
					this.arrowShake = 7;
					this.setIsCritical(false);

					if (this.inTile.getMaterial() != Material.air)
					{
						this.inTile.onEntityCollidedWithBlock(this.worldObj, this.xTile, this.yTile, this.zTile, this);
					}
				}
			}
			if (this.getIsCritical()) 
			{
				for (i = 0; i < 4; ++i)
				{
					this.worldObj.spawnParticle("crit", this.posX + this.motionX * (double)i / 4.0D, this.posY + this.motionY * (double)i / 4.0D, this.posZ + this.motionZ * (double)i / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
				}
			}
			this.posX += this.motionX;
			this.posY += this.motionY;
			this.posZ += this.motionZ;
			f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
			for (this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f2) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
			{
				;
			}

			while (this.rotationPitch - this.prevRotationPitch >= 180.0F) 
			{
				this.prevRotationPitch += 360.0F;
			}
			while (this.rotationYaw - this.prevRotationYaw < -180.0F) 
			{
				this.prevRotationYaw -= 360.0F;
			}
			while (this.rotationYaw - this.prevRotationYaw >= 180.0F) 
			{
				this.prevRotationYaw += 360.0F;
			}
			this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
			this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
			float f3 = 0.99F;
			f1 = 0.05F;

			if (this.isInWater())
			{
				for (int l = 0; l < 4; ++l)
				{
					f4 = 0.25F;
					this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)f4, this.posY - this.motionY * (double)f4, this.posZ - this.motionZ * (double)f4, this.motionX, this.motionY, this.motionZ);
				}

				f3 = 0.8F;
			}

			if (this.isWet())
			{
				this.extinguish();
			}

			this.motionX *= (double)f3;
			this.motionY *= (double)f3;
			this.motionZ *= (double)f3;
			this.motionY -= (double)f1;
			this.setPosition(this.posX, this.posY, this.posZ);
			this.func_145775_I();

			if((this.isDead || this.arrowShake > 0) && this.explosive)
			{
				this.worldObj.newExplosion(null, posX, posY, posZ, 2f, false, false);
				this.kill();
			}
		}
	}

	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		this.xTile = par1NBTTagCompound.getShort("xTile");
		this.yTile = par1NBTTagCompound.getShort("yTile");
		this.zTile = par1NBTTagCompound.getShort("zTile");
		this.ticksInGround = par1NBTTagCompound.getShort("life");
		this.inTile = Block.getBlockById(par1NBTTagCompound.getByte("inTile") & 255);
		this.inData = par1NBTTagCompound.getByte("inData") & 255;
		this.arrowShake = par1NBTTagCompound.getByte("shake") & 255;
		this.inGround = par1NBTTagCompound.getByte("inGround") == 1;

		if (par1NBTTagCompound.hasKey("damage", 99))
		{
			this.damage = par1NBTTagCompound.getDouble("damage");
		}

		if (par1NBTTagCompound.hasKey("pickup", 99))
		{
			this.canBePickedUp = par1NBTTagCompound.getByte("pickup");
		}
		else if (par1NBTTagCompound.hasKey("player", 99))
		{
			this.canBePickedUp = par1NBTTagCompound.getBoolean("player") ? 1 : 0;
		}
	}

	public void setDamage(double par1)
	{
		this.damage = par1;
	}

	public void setExplosive(boolean par1)
	{
		this.explosive = par1;
	}

	public void setIsCritical(boolean par1)
	{
		byte var2 = this.dataWatcher.getWatchableObjectByte(16);
		if (par1) 
		{
			this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 1)));
		} 
		else 
		{
			this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & -2)));
		}
	}

	public void setKnockbackStrength(int par1)
	{
		this.knockbackStrength = par1;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
	{
		this.setPosition(par1, par3, par5);
		this.setRotation(par7, par8);
	}

	public void setThrowableHeading(double par1, double par3, double par5, float par7, float par8)
	{
		float var9 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
		par1 /= (double)var9;
		par3 /= (double)var9;
		par5 /= (double)var9;
		par1 += this.rand.nextGaussian() * 0.007499999832361937D * (double)par8;
		par3 += this.rand.nextGaussian() * 0.007499999832361937D * (double)par8;
		par5 += this.rand.nextGaussian() * 0.007499999832361937D * (double)par8;
		par1 *= (double)par7;
		par3 *= (double)par7;
		par5 *= (double)par7;
		this.motionX = par1;
		this.motionY = par3;
		this.motionZ = par5;
		float var10 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
		this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
		this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(par3, (double)var10) * 180.0D / Math.PI);
		this.ticksInGround = 0;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void setVelocity(double par1, double par3, double par5)
	{
		this.motionX = par1;
		this.motionY = par3;
		this.motionZ = par5;
		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
		{
			float var7 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
			this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(par3, (double)var7) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch;
			this.prevRotationYaw = this.rotationYaw;
			this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
			this.ticksInGround = 0;
		}
	}

	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		par1NBTTagCompound.setShort("xTile", (short)this.xTile);
		par1NBTTagCompound.setShort("yTile", (short)this.yTile);
		par1NBTTagCompound.setShort("zTile", (short)this.zTile);
		par1NBTTagCompound.setShort("life", (short)this.ticksInGround);
		par1NBTTagCompound.setByte("inTile", (byte)Block.getIdFromBlock(this.inTile));
		par1NBTTagCompound.setByte("inData", (byte)this.inData);
		par1NBTTagCompound.setByte("shake", (byte)this.arrowShake);
		par1NBTTagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
		par1NBTTagCompound.setByte("pickup", (byte)this.canBePickedUp);
		par1NBTTagCompound.setDouble("damage", this.damage);
	}
}