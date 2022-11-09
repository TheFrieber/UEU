package plugin;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
	@Override
	public void onEnable()
	{
		this.getConfig();
		this.getConfig().addDefault("power.normal", 0.08D);
		this.getConfig().addDefault("power.underwater", Integer.valueOf(1));
		this.getConfig().addDefault("particles.enabled", true);
		this.getConfig().addDefault("particles.type", "CLOUD");
		this.getConfig().addDefault("particles.x", Integer.valueOf(0));
		this.getConfig().addDefault("particles.y", Integer.valueOf(0));
		this.getConfig().addDefault("particles.z", Integer.valueOf(0));
		this.getConfig().addDefault("particles.amount", Integer.valueOf(4));
		this.getConfig().addDefault("particles.offsetX", Integer.valueOf(0));
		this.getConfig().addDefault("particles.offsetZ", Integer.valueOf(0));
		this.getConfig().addDefault("particles.offsetY", Integer.valueOf(0));
		this.getConfig().addDefault("enable_elytra_underwater", true);
		this.getConfig().addDefault("particles_underwater.enabled", true);
		this.getConfig().addDefault("particles_underwater.type", "BUBBLE_POP");
		this.getConfig().addDefault("particles_underwater.x", Integer.valueOf(0));
		this.getConfig().addDefault("particles_underwater.y", Integer.valueOf(0));
		this.getConfig().addDefault("particles_underwater.z", Integer.valueOf(0));
		this.getConfig().addDefault("particles_underwater.amount", Integer.valueOf(10));
		this.getConfig().addDefault("particles_underwater.offsetX", Integer.valueOf(0));
		this.getConfig().addDefault("particles_underwater.offsetZ", Integer.valueOf(0));
		this.getConfig().addDefault("particles_underwater.offsetY", Integer.valueOf(0));
		this.getConfig().addDefault("boost.underwater", true);
		this.getConfig().addDefault("boost.normal", true);
		this.getConfig().addDefault("TheFriebers_options.Nofall_NoWall_damage_on_wearing_wing", true);
		this.getConfig().addDefault(
				"TheFriebers_options.Nofall_NoWall_damage_on_HoldingInCursor_wing_ENABLE_ON_WEARING_WING_TOO", true);
		this.getConfig().addDefault("TheFriebers_options.Nofall_NoWall_damage_on_Havin_wing_in_Inventory", false);
		this.getConfig().addDefault("TheFriebers_options.Stop_Gliding_if_out_of_fuel_ONLY_WORKS_WITH_FUEL_ENABLED",
				false);
		this.getConfig().addDefault(
				"TheFriebers_options.Dont_Get_FallDamage_After_Fuel_Is_Zero_ONLY_WORKS_IF_STOP_GLIDING_IS_TRUE", true);
		this.getConfig().addDefault("TheFriebers_options.Unbreakable_wing", true);
		this.getConfig().addDefault("TheFriebers_options.Use_Fuel_Coal", true);
		this.getConfig().addDefault("TheFriebers_options.Only_allow_boosting_if_player_looks_up", false);
		this.getConfig().addDefault("TheFriebers_options.Sprinting_antimation_on_boosting", true);
		this.getConfig().addDefault("TheFriebers_options.Play_Sound_on_boosting_only_works_with_usefuel_enabled", true);
		this.getConfig().addDefault("TheFriebers_options.Play_Sound_High_deep_HigherVaule_HigherSound", 0.8F);
		this.getConfig().addDefault("TheFriebers_options.Play_Sound_Volume", 1.0F);
		this.getConfig().addDefault("TheFriebers_options.Fuel_Usage_Time_MAX_1", 0.20d);
		this.getConfig().addDefault("TheFriebers_options.Fuel_Usage_Item", Integer.valueOf(1));
		this.getConfig().addDefault("TheFriebers_advanced_options.power_not_smooth", 1.0D);
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
		this.getServer().getPluginManager().registerEvents(this, this);
	}

	public static class Global
	{
		public static String Stop_Gliding;
		public static double Fuel_Usage_Vaule;
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event)
	{
		try
		{
			if (event.getEntity() instanceof Player)
			{
				if (this.getConfig().getBoolean(
						"TheFriebers_options.Dont_Get_FallDamage_After_Fuel_Is_Zero_ONLY_WORKS_IF_STOP_GLIDING_IS_TRUE"))
				{
					if (Global.Stop_Gliding == "true")
					{
						Player p = (Player) event.getEntity();
						if (Global.Stop_Gliding == "true")
						{
							Global.Stop_Gliding = "false";
							if (event.getCause() == DamageCause.FALL || event.getCause() == DamageCause.FLY_INTO_WALL)
							{
								event.setCancelled(true);
							}
							if (p.getLastDamageCause().getCause() == DamageCause.FALL
									|| event.getCause() == DamageCause.FLY_INTO_WALL)
							{
								event.setCancelled(true);
							}
						}
					} else
					{

					}
				}
				if (!this.getConfig()
						.getBoolean("TheFriebers_options.Nofall_NoWall_damage_on_Havin_wing_in_Inventory"))
				{
					Player p = (Player) event.getEntity();
					if (this.getConfig().getBoolean("TheFriebers_options.Nofall_NoWall_damage_on_wearing_wing"))
					{
						try
						{
							ItemStack stack = p.getInventory().getChestplate();
							Material a = stack.getType();
							if (a == Material.ELYTRA)
							{
								if (event.getCause() == DamageCause.FALL
										|| event.getCause() == DamageCause.FLY_INTO_WALL)
								{
									event.setCancelled(true);
								}
								if (p.getLastDamageCause().getCause() == DamageCause.FALL
										|| event.getCause() == DamageCause.FLY_INTO_WALL)
								{
									event.setCancelled(true);
								}
							}
						} catch (Exception e)
						{
							if (this.getConfig().getBoolean(
									"TheFriebers_options.Nofall_NoWall_damage_on_HoldingInCursor_wing"))
							{
								ItemStack inhandstack = p.getItemOnCursor();
								Material b = inhandstack.getType();
								if (b == Material.ELYTRA)
								{
									if (event.getCause() == DamageCause.FALL
											|| event.getCause() == DamageCause.FLY_INTO_WALL)
									{
										event.setCancelled(true);
									}
									if (p.getLastDamageCause().getCause() == DamageCause.FALL
											|| event.getCause() == DamageCause.FLY_INTO_WALL)
									{
										event.setCancelled(true);
									}
								}
							}
						}

					}
				}

				else if (this.getConfig()
						.getBoolean("TheFriebers_options.Nofall_NoWall_damage_on_Havin_wing_in_Inventory"))
				{
					Player p = (Player) event.getEntity();
					try
					{
						ItemStack stack = p.getInventory().getChestplate();
						Material a = stack.getType();
						if (a == Material.ELYTRA)
						{
							if (event.getCause() == DamageCause.FALL || event.getCause() == DamageCause.FLY_INTO_WALL)
							{
								event.setCancelled(true);
							}
							if (p.getLastDamageCause().getCause() == DamageCause.FALL
									|| event.getCause() == DamageCause.FLY_INTO_WALL)
							{
								event.setCancelled(true);
							}
						}
					} catch (Exception e)
					{
						try
						{
							if (this.getConfig().getBoolean(
									"TheFriebers_options.Nofall_NoWall_damage_on_HoldingInCursor_wing_ENABLE_ON_WEARING_WING_TOO"))
							{
								ItemStack inhandstack = p.getItemOnCursor();
								Material b = inhandstack.getType();
								if (b == Material.ELYTRA)
								{
									if (event.getCause() == DamageCause.FALL
											|| event.getCause() == DamageCause.FLY_INTO_WALL)
									{
										event.setCancelled(true);
									}
									if (p.getLastDamageCause().getCause() == DamageCause.FALL
											|| event.getCause() == DamageCause.FLY_INTO_WALL)
									{
										event.setCancelled(true);
									}
								} else
								{
									if (this.getConfig().getBoolean(
											"TheFriebers_options.Nofall_NoWall_damage_on_HoldingInCursor_wing"))
									{
										p.getInventory().getContents();
										if (p.getInventory().contains(Material.ELYTRA))
										{
											if (event.getCause() == DamageCause.FALL
													|| event.getCause() == DamageCause.FLY_INTO_WALL)
											{
												event.setCancelled(true);
											}
											if (p.getLastDamageCause().getCause() == DamageCause.FALL
													|| event.getCause() == DamageCause.FLY_INTO_WALL)
											{
												event.setCancelled(true);
											}
										}
									}
								}
							}
						} catch (Exception s)
						{
							if (this.getConfig().getBoolean(
									"TheFriebers_options.Nofall_NoWall_damage_on_HoldingInCursor_wing"))
							{
								p.getInventory().getContents();
								if (p.getInventory().contains(Material.ELYTRA))
								{
									if (event.getCause() == DamageCause.FALL
											|| event.getCause() == DamageCause.FLY_INTO_WALL)
									{
										event.setCancelled(true);
									}
									if (p.getLastDamageCause().getCause() == DamageCause.FALL
											|| event.getCause() == DamageCause.FLY_INTO_WALL)
									{
										event.setCancelled(true);
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e)
		{
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e)
	{
		if (!this.getConfig().getBoolean("TheFriebers_options.Use_Fuel_Coal"))
		{
			Player p = e.getPlayer();
			if (p.hasPermission("unlimitedelytra.boost"))
			{
				float pitch = p.getLocation().getPitch();
				if (this.getConfig().getBoolean("TheFriebers_options.Only_allow_boosting_if_player_looks_up"))
				{
					if (pitch < -13)
					{
						Material m = p.getLocation().getBlock().getType();
						if (m == Material.WATER && this.getConfig().getBoolean("boost.underwater"))
						{
							if (p.isGliding() && p.isSneaking())
							{
								p.setVelocity(p.getLocation().getDirection()
										.multiply(this.getConfig().getDouble("power.underwater")));
							} else if (this.getConfig().getBoolean("enable_elytra_underwater")
									&& e.getFrom().getY() < e.getTo().getY() && p.isSneaking()
									&& p.getInventory().getChestplate().getType() == Material.ELYTRA)
							{
								p.setGliding(true);
							}
						} else if (p.isGliding() && p.isSneaking() && this.getConfig().getBoolean("boost.normal"))
						{
							if (this.getConfig()
									.getBoolean("TheFriebers_options.Sprinting_antimation_on_boosting"))
							{
								p.setSprinting(true);
							}
							p.setVelocity(p.getVelocity().add(p.getLocation().getDirection()
									.multiply(this.getConfig().getDouble("power.normal"))));
						} else if (this.getConfig()
								.getBoolean("TheFriebers_options.Sprinting_antimation_on_boosting")
								&& !p.isSneaking() && p.isGliding())
						{
							p.setSprinting(false);
						}

						if (p.isGliding() && p.isSneaking())
						{
							try
							{
								if ((m == Material.WATER)
										&& this.getConfig().getBoolean("particles_underwater.enabled"))
								{
									p.getWorld().spawnParticle(
											Particle.valueOf(this.getConfig().getString("particles_underwater.type")),
											p.getLocation().getX()
													+ this.getConfig().getInt("particles_underwater.offsetX"),
											p.getLocation().getY()
													+ this.getConfig().getInt("particles_underwater.offsetY"),
											p.getLocation().getZ()
													+ this.getConfig().getInt("particles_underwater.offsetZ"),
											this.getConfig().getInt("particles_underwater.amount"),
											this.getConfig().getInt("particles_underwater.x"),
											this.getConfig().getInt("particles_underwater.y"),
											this.getConfig().getInt("particles_underwater.z"));
								} else if (this.getConfig().getBoolean("particles.enabled"))
								{
									p.getWorld().spawnParticle(
											Particle.valueOf(this.getConfig().getString("particles.type")),
											p.getLocation().getX() + this.getConfig().getInt("particles.offsetX"),
											p.getLocation().getY() + this.getConfig().getInt("particles.offsetY"),
											p.getLocation().getZ() + this.getConfig().getInt("particles.offsetZ"),
											this.getConfig().getInt("particles.amount"),
											this.getConfig().getInt("particles.x"),
											this.getConfig().getInt("particles.y"),
											this.getConfig().getInt("particles.z"));
								}
							} catch (NullPointerException var5)
							{
								this.getServer().getConsoleSender().sendMessage("<!> Error: invalid particle type");
							}
						}
					}
				} else if (!this.getConfig()
						.getBoolean("TheFriebers_options.Only_allow_boosting_if_player_looks_up"))
				{
					Material m = p.getLocation().getBlock().getType();
					if (m == Material.WATER && this.getConfig().getBoolean("boost.underwater"))
					{
						if (p.isGliding() && p.isSneaking())
						{
							p.setVelocity(p.getLocation().getDirection()
									.multiply(this.getConfig().getDouble("power.underwater")));
						} else if (this.getConfig().getBoolean("enable_elytra_underwater")
								&& e.getFrom().getY() < e.getTo().getY() && p.isSneaking()
								&& p.getInventory().getChestplate().getType() == Material.ELYTRA)
						{
							p.setGliding(true);
						}
					} else if (p.isGliding() && p.isSneaking() && this.getConfig().getBoolean("boost.normal"))
					{
						if (this.getConfig().getBoolean("TheFriebers_options.Sprinting_antimation_on_boosting"))
						{
							p.setSprinting(true);
						}
						p.setVelocity(p.getVelocity().add(
								p.getLocation().getDirection().multiply(this.getConfig().getDouble("power.normal"))));
					}

					if (p.isGliding() && p.isSneaking())
					{
						try
						{
							if ((m == Material.WATER) && this.getConfig().getBoolean("particles_underwater.enabled"))
							{
								p.getWorld().spawnParticle(
										Particle.valueOf(this.getConfig().getString("particles_underwater.type")),
										p.getLocation().getX()
												+ this.getConfig().getInt("particles_underwater.offsetX"),
										p.getLocation().getY()
												+ this.getConfig().getInt("particles_underwater.offsetY"),
										p.getLocation().getZ()
												+ this.getConfig().getInt("particles_underwater.offsetZ"),
										this.getConfig().getInt("particles_underwater.amount"),
										this.getConfig().getInt("particles_underwater.x"),
										this.getConfig().getInt("particles_underwater.y"),
										this.getConfig().getInt("particles_underwater.z"));
							} else if (this.getConfig().getBoolean("particles.enabled"))
							{
								p.getWorld().spawnParticle(
										Particle.valueOf(this.getConfig().getString("particles.type")),
										p.getLocation().getX() + this.getConfig().getInt("particles.offsetX"),
										p.getLocation().getY() + this.getConfig().getInt("particles.offsetY"),
										p.getLocation().getZ() + this.getConfig().getInt("particles.offsetZ"),
										this.getConfig().getInt("particles.amount"),
										this.getConfig().getInt("particles.x"), this.getConfig().getInt("particles.y"),
										this.getConfig().getInt("particles.z"));
							}
						} catch (NullPointerException var5)
						{
							this.getServer().getConsoleSender().sendMessage("<!> Error: invalid particle type");
						}
					}
				}

			}
		} else
		{
			Player p = e.getPlayer();
			if (p.hasPermission("unlimitedelytra.boost"))
			{
				float sounder = (float) this.getConfig()
						.getDouble("TheFriebers_options.Play_Sound_High_deep_HigherVaule_HigherSound");
				float volume = (float) this.getConfig().getDouble("TheFriebers_options.Play_Sound_Volume");

				float pitch = p.getLocation().getPitch();
				if (this.getConfig().getBoolean("TheFriebers_options.Only_allow_boosting_if_player_looks_up"))
				{
					if (pitch < -13)
					{
						if (p.isGliding() && p.isSneaking())
						{
							p.getInventory().getContents();

							if (p.getInventory().contains(Material.CHARCOAL))
							{
								if (p.hasPermission("unlimitedelytra.boost"))
								{
									for (int i = 0; i < p.getInventory().getSize(); i++)
									{

										ItemStack itm = p.getInventory().getItem(i);

										if (itm != null && itm.getType().equals(Material.CHARCOAL))
										{
											if (Global.Fuel_Usage_Vaule > 1d)
											{
												Global.Fuel_Usage_Vaule = 0d;
												int Fuel_Usage_Item = this.getConfig()
														.getInt("TheFriebers_options.Fuel_Usage_Item");
												int amt = itm.getAmount() - Fuel_Usage_Item;

												itm.setAmount(amt);

												p.getInventory().setItem(i, amt > 0 ? itm : null);

												p.updateInventory();

												break;
											} else
											{
												double Fuel_Usage_Time = this.getConfig()
														.getDouble("TheFriebers_options.Fuel_Usage_Time_MAX_1");
												Global.Fuel_Usage_Vaule += Fuel_Usage_Time;
												break;
											}
										}
									}
									Material m = p.getLocation().getBlock().getType();
									if (m == Material.WATER && this.getConfig().getBoolean("boost.underwater"))
									{
										if (p.isGliding() && p.isSneaking())
										{
											p.setVelocity(p.getLocation().getDirection()
													.multiply(this.getConfig().getDouble("power.underwater")));
										} else if (this.getConfig().getBoolean("enable_elytra_underwater")
												&& e.getFrom().getY() < e.getTo().getY() && p.isSneaking()
												&& p.getInventory().getChestplate().getType() == Material.ELYTRA)
										{
											p.setGliding(true);
										}
									} else if (p.isGliding() && p.isSneaking()
											&& this.getConfig().getBoolean("boost.normal"))
									{
										if (this.getConfig().getBoolean(
												"TheFriebers_options.Sprinting_antimation_on_boosting"))
										{
											p.setSprinting(true);
										}
										if (this.getConfig().getBoolean(
												"TheFriebers_options.Play_Sound_on_boosting_only_works_with_usefuel_enabled"))
										{
											p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, volume, sounder);
										}
										p.setVelocity(p.getVelocity().add(p.getLocation().getDirection()
												.multiply(this.getConfig().getDouble("power.normal"))));
									}

									if (p.isGliding() && p.isSneaking())
									{
										try
										{
											if ((m == Material.WATER)
													&& this.getConfig().getBoolean("particles_underwater.enabled"))
											{
												p.getWorld().spawnParticle(
														Particle.valueOf(this.getConfig()
																.getString("particles_underwater.type")),
														p.getLocation().getX() + this.getConfig()
																.getInt("particles_underwater.offsetX"),
														p.getLocation().getY() + this.getConfig()
																.getInt("particles_underwater.offsetY"),
														p.getLocation().getZ() + this.getConfig()
																.getInt("particles_underwater.offsetZ"),
														this.getConfig().getInt("particles_underwater.amount"),
														this.getConfig().getInt("particles_underwater.x"),
														this.getConfig().getInt("particles_underwater.y"),
														this.getConfig().getInt("particles_underwater.z"));
											} else if (this.getConfig().getBoolean("particles.enabled"))
											{
												p.getWorld().spawnParticle(
														Particle.valueOf(this.getConfig().getString("particles.type")),
														p.getLocation().getX()
																+ this.getConfig().getInt("particles.offsetX"),
														p.getLocation().getY()
																+ this.getConfig().getInt("particles.offsetY"),
														p.getLocation().getZ()
																+ this.getConfig().getInt("particles.offsetZ"),
														this.getConfig().getInt("particles.amount"),
														this.getConfig().getInt("particles.x"),
														this.getConfig().getInt("particles.y"),
														this.getConfig().getInt("particles.z"));
											}
										} catch (NullPointerException var5)
										{
											this.getServer().getConsoleSender()
													.sendMessage("<!> Error: invalid particle type");
										}
									}
								}
							} else
							{
								p.sendMessage(ChatColor.DARK_RED + "Im Out of Fuel!");
								if (this.getConfig().getBoolean(
										"TheFriebers_options.Stop_Gliding_if_out_of_fuel_ONLY_WORKS_WITH_FUEL_ENABLED"))
								{
									Global.Stop_Gliding = "true";
									p.setGliding(false);
								}
							}
						}
					}
				} else if (!this.getConfig()
						.getBoolean("TheFriebers_options.Only_allow_boosting_if_player_looks_up"))
				{
					if (p.isGliding() && p.isSneaking())
					{
						p.getInventory().getContents();

						if (p.getInventory().contains(Material.CHARCOAL))
						{
							if (p.hasPermission("unlimitedelytra.boost"))
							{
								for (int i = 0; i < p.getInventory().getSize(); i++)
								{

									ItemStack itm = p.getInventory().getItem(i);

									if (itm != null && itm.getType().equals(Material.CHARCOAL))
									{
										if (Global.Fuel_Usage_Vaule > 1d)
										{
											Global.Fuel_Usage_Vaule = 0d;
											int Fuel_Usage_Item = this.getConfig()
													.getInt("TheFriebers_options.Fuel_Usage_Item");
											int amt = itm.getAmount() - Fuel_Usage_Item;

											itm.setAmount(amt);

											p.getInventory().setItem(i, amt > 0 ? itm : null);

											p.updateInventory();

											break;
										} else
										{
											double Fuel_Usage_Time = this.getConfig()
													.getDouble("TheFriebers_options.Fuel_Usage_Time_MAX_1");
											Global.Fuel_Usage_Vaule += Fuel_Usage_Time;
											break;
										}
									}
								}
								Material m = p.getLocation().getBlock().getType();
								if (m == Material.WATER && this.getConfig().getBoolean("boost.underwater"))
								{
									if (p.isGliding() && p.isSneaking())
									{
										p.setVelocity(p.getLocation().getDirection()
												.multiply(this.getConfig().getDouble("power.underwater")));
									} else if (this.getConfig().getBoolean("enable_elytra_underwater")
											&& e.getFrom().getY() < e.getTo().getY() && p.isSneaking()
											&& p.getInventory().getChestplate().getType() == Material.ELYTRA)
									{
										p.setGliding(true);
									}
								} else if (p.isGliding() && p.isSneaking()
										&& this.getConfig().getBoolean("boost.normal"))
								{
									if (this.getConfig()
											.getBoolean("TheFriebers_options.Sprinting_antimation_on_boosting"))
									{
										p.setSprinting(true);
									}
									if (this.getConfig().getBoolean(
											"TheFriebers_options.Play_Sound_on_boosting_only_works_with_usefuel_enabled"))
									{
										p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, volume, sounder);
									}
									p.setVelocity(p.getVelocity().add(p.getLocation().getDirection()
											.multiply(this.getConfig().getDouble("power.normal"))));
								}

								if (p.isGliding() && p.isSneaking())
								{
									try
									{
										if ((m == Material.WATER)
												&& this.getConfig().getBoolean("particles_underwater.enabled"))
										{
											p.getWorld().spawnParticle(
													Particle.valueOf(
															this.getConfig().getString("particles_underwater.type")),
													p.getLocation().getX()
															+ this.getConfig().getInt("particles_underwater.offsetX"),
													p.getLocation().getY()
															+ this.getConfig().getInt("particles_underwater.offsetY"),
													p.getLocation().getZ()
															+ this.getConfig().getInt("particles_underwater.offsetZ"),
													this.getConfig().getInt("particles_underwater.amount"),
													this.getConfig().getInt("particles_underwater.x"),
													this.getConfig().getInt("particles_underwater.y"),
													this.getConfig().getInt("particles_underwater.z"));
										} else if (this.getConfig().getBoolean("particles.enabled"))
										{
											p.getWorld().spawnParticle(
													Particle.valueOf(this.getConfig().getString("particles.type")),
													p.getLocation().getX()
															+ this.getConfig().getInt("particles.offsetX"),
													p.getLocation().getY()
															+ this.getConfig().getInt("particles.offsetY"),
													p.getLocation().getZ()
															+ this.getConfig().getInt("particles.offsetZ"),
													this.getConfig().getInt("particles.amount"),
													this.getConfig().getInt("particles.x"),
													this.getConfig().getInt("particles.y"),
													this.getConfig().getInt("particles.z"));
										}
									} catch (NullPointerException var5)
									{
										this.getServer().getConsoleSender()
												.sendMessage("<!> Error: invalid particle type");
									}
								}
							}
						} else
						{
							p.sendMessage(ChatColor.DARK_RED + "Im Out of Fuel!");
							if (this.getConfig().getBoolean(
									"TheFriebers_options.Stop_Gliding_if_out_of_fuel_ONLY_WORKS_WITH_FUEL_ENABLED"))
							{
								Global.Stop_Gliding = "true";
								p.setGliding(false);
							}
						}
					}
				}
			}

		}
	}

	@EventHandler
	public void onItemDamage(PlayerItemDamageEvent e)
	{
		if (this.getConfig().getBoolean("TheFriebers_options.Unbreakable_wing"))
		{
			if (e.getItem().getType() == Material.ELYTRA && e.getPlayer().isGliding()
					&& e.getPlayer().hasPermission("unlimitedelytra.boost"))
			{
				ItemStack i = e.getItem();
				i.setDurability((short) 0);
				e.getPlayer().getInventory().setChestplate(i);
			}

		}
	}

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args)
	{

		if (cmd.getName().equalsIgnoreCase("unlimitedelytra"))
		{
			if (s.hasPermission("unlimitedelytra.cmd.main"))
			{
				if (args.length != 0 && args.length <= 1)
				{
					if (args[0].equalsIgnoreCase("reload"))
					{
						if (s.hasPermission("unlimitedelytra.cmd.reload"))
						{
							s.sendMessage("§b§n'UnlimitedElytra'§b succesfully reloaded.");
							this.reloadConfig();
							return true;
						} else
						{
							s.sendMessage("insufficient permissions");
							return true;
						}
					} else if (args[0].equalsIgnoreCase("reset"))
					{
						if (s.hasPermission("unlimitedelytra.cmd.reset"))
						{
							File file = new File(this.getDataFolder(), "config.yml");
							file.delete();
							this.reloadConfig();
							this.getConfig().options().copyDefaults(true);
							this.saveConfig();
							s.sendMessage("§b§nUnlimitedElytra§b's config succesfully reset.");
							return true;
						} else
						{
							s.sendMessage("insufficient permissions");
							return true;
						}
					} else
					{
						s.sendMessage("§c<!> Usage: '/unlimitedelytra <reload|reset>'");
						return true;
					}
				} else
				{
					s.sendMessage("§c<!> Usage: '/unlimitedelytra <reload|reset>'");
					return true;
				}
			} else
			{
				s.sendMessage("insufficient permissions");
				return true;
			}
		} else
		{
			return false;
		}
	}
}
