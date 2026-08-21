package gg.vape.mapping.runtime;

/**
 * Identity remap table for mojmap-name runtimes (NeoForge 1.20.1 / 1.21.1):
 * member names already match the runtime, so no remapping is registered and
 * lookups fall back to the source (mojmap) name.
 */
public class MemberNameRemapTableIdentity
extends MemberNameRemapTable {
}
