package org.rmmcosta

class MachineCountingVisitor(
    private var count: Int = 0
) : MachineVisitor {
    override fun visit(machine: Machine) {
        count++
    }

    override fun visit(machineComposite: MachineComposite) {
        machineComposite.getComponents().forEach { it.accept(this) }
    }

    fun count() = count
}

class FindVisitor(
    private val soughtId: Int
) : MachineVisitor {
    var result: MachineComponent? = null
        private set

    override fun visit(machine: Machine) {
        if (soughtId == machine.id) result = machine
    }

    override fun visit(machineComposite: MachineComposite) {
        if (machineComposite.id == soughtId) {
            result = machineComposite
            return
        }
        for (machine in machineComposite.getComponents()) {
            if (result != null) return
            println("next accept")
            machine.accept(this)
        }
    }

    companion object {
        fun find(machineComponent: MachineComponent, id: Int): MachineComponent? {
            val visitor = FindVisitor(id)
            machineComponent.accept(visitor)
            return visitor.result
        }
    }
}
