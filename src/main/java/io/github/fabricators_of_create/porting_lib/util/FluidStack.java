package io.github.fabricators_of_create.porting_lib.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;

// Dummy class to not include jar
public class FluidStack {
    public FluidStack() {}

    public Fluid getFluid() { return null; }
    public @NotNull CompoundTag getTag() { return null; }
    public long getAmount() { return 0L; }
}
