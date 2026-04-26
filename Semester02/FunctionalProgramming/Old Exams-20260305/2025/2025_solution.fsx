type stack<'b> = list<'b>
type mapStack<'a,'b> = MapStack of list<'a*stack<'b>>

// The map–stack ex1 above may be implemented as follows:
let ex1 = MapStack [('1',[3;1;1]); ('2',[1;3;1]); ('3',[1;1;3]); ('4',[3;3;1]); ('5',[2;3;4]); ('6',[2;1;2])]

// Question 1.1
// Declare a MapStack for the other table
let ex2 = MapStack [("Hey", ['H'; 'E'; 'Y']); ("there", ['t'; 'h'; 'e'; 'r'; 'e']); ("", [])]
// Ex2 type: string * char list

//Declare an F# function empty() of type unit-> mapStack<’a,’b> that evaluates to an empty map–stack.
let empty () : mapStack<'a,'b> = MapStack []

// Declare an F# function numKeys m of type mapStack<’a,’b>-> int that evaluates to the
// number of keys in the map–stack m. For instance numKeys ex1 evaluates to 6
let rec numKeys m : int = 
    match m with 
    | MapStack [] -> 0 
    | MapStack (x::rest) -> 1 + numKeys (MapStack rest)

numKeys ex2

// Declare an F# function numValues m of type mapStack<’a,’b>-> int that evaluates to
// the number of values in the stacks in the map–stack m. For instance numValues ex1 evaluates to 18. (counting, count, open list, get values)

let rec numValues m : int =
    match m with
    | MapStack [] -> 0
    | MapStack ((_, values)::rest) ->
        List.length values + numValues (MapStack rest)

numValues ex2

// Question 1.2 
