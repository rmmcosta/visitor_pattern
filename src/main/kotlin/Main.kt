package org.rmmcosta

fun main() {
    println("Hello World!")
    val mc1 = Machine(1)
    val mc2 = Machine(2)
    val mc3 = Machine(3)
    val root = MachineComposite(listOf(mc1, mc2, mc3), 44)
    val machineCountingVisitor = MachineCountingVisitor()
    root.accept(machineCountingVisitor)
    println(machineCountingVisitor.count())
    val machine100 = Machine(100)
    machine100.accept(machineCountingVisitor)
    println(machineCountingVisitor.count())
    val findVisitor = FindVisitor()
    println(findVisitor.find(machine100,100))
    println(findVisitor.find(machine100,101))
    println("find 1 inside composite")
    println(findVisitor.find(root,1))
    println("find 2 inside composite")
    println(findVisitor.find(root,2))
    println("find 3 inside composite")
    println(findVisitor.find(root,3))
    println("find 4 inside composite")
    println(findVisitor.find(root,4))
}

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
    private var soughtId: Int? = null,
    private var sought: MachineComponent? = null,
) : MachineVisitor {
    override fun visit(machine: Machine) {
        if (soughtId == machine.id)
            sought = machine
    }

    override fun visit(machineComposite: MachineComposite) {
        machineComposite.getComponents().forEach {
            if (sought != null)
                return
            println("accept next")
            it.accept(this)
        }
    }

    fun find(machineComponent: MachineComponent, id: Int): MachineComponent? {
        soughtId = id
        sought = null
        machineComponent.accept(this)
        return sought
    }
}