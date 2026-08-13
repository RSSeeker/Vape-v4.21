package gg.vape.module.control;

import gg.vape.input.MouseButtonInputLock;
import gg.vape.input.MovementInputLock;
import gg.vape.rotation.RotationControlClaim;

public class SharedModuleControlClaims {
    public static final PrimaryActionControlClaim primaryAction = new PrimaryActionControlClaim();
    public static final MouseButtonInputLock mouseButtons = new MouseButtonInputLock();
    public static final SecondaryActionControlClaim secondaryAction = new SecondaryActionControlClaim();
    public static final RightClickUseControlClaim rightClickUse = new RightClickUseControlClaim();
    public static final MovementInputLock movementInput = new MovementInputLock();
    public static final RenderPassControlClaim renderPass = new RenderPassControlClaim();
    public static final RotationControlClaim rotation = new RotationControlClaim();
    public static final MouseOverUpdateControlClaim mouseOverUpdate = new MouseOverUpdateControlClaim();
}
