package org.rmmcosta

abstract class MachineComponent(
    open val id: Int,
    open val parent: MachineComponent? = null,
) {
    abstract fun getMachineCount(): Int
    abstract fun accept(visitor: MachineVisitor)
}

class MachineComposite(
    private val components: List<MachineComponent>,
    id: Int,
    parent: MachineComponent? = null,
) : MachineComponent(id, parent) {
    override fun getMachineCount() = components.sumOf { it.getMachineCount() }

    override fun accept(visitor: MachineVisitor) = visitor.visit(this)

    fun getComponents(): List<MachineComponent> = components.toList()
}

data class Machine(
    override val id: Int,
    override val parent: MachineComponent? = null,
) : MachineComponent(id, parent) {
    override fun getMachineCount() = 1

    override fun accept(visitor: MachineVisitor) = visitor.visit(this)
}

interface MachineVisitor {
    fun visit(machine: Machine)
    fun visit(machineComposite: MachineComposite)
}