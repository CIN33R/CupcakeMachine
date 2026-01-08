# CupcakeMachine Mini Project (Classes + Constructor + Method)

## Your Task
Complete the `CupcakeMachine` class by finishing:
- the constructor
- the `takeOrder` method

You are being graded by automated tests (autograding). Your code must match the required behavior exactly.

---

## Required Behavior

### Instance Variables (already declared for you)
- `cupcakesInMachine` (int)
- `costPerCupcake` (double, **final**)
- `nextOrderNumber` (int)

### Constructor
`public CupcakeMachine(int numCupcakes, double costPerCupcake)`

When a machine is created:
- `cupcakesInMachine` starts at `numCupcakes`
- `costPerCupcake` is set from the parameter
- `nextOrderNumber` starts at `1`

### takeOrder
`public String takeOrder(int cupcakesOrdered)`

Rules:
1. If `cupcakesOrdered` is **more than** `cupcakesInMachine`, return:
    - `"Order cannot be filled"`
2. Otherwise, return a message like:
    - `"Order number 1, cost $7.5"`
3. After a successful order:
    - subtract cupcakes ordered from `cupcakesInMachine`
    - increase `nextOrderNumber` by 1

---

## Running Tests Locally (optional)
If you have Gradle installed, you can run:
```bash
./gradlew test
