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
// Declare an F# function pop s of type ’a list-> ’a*’a list that evaluates to a pair (sh,st)
// where s = sh :: st. That is, sh is the head element and st is the tail of the list s. In case s is
// empty, an exception, System.Exception, is thrown containing an error message: “Can’t pop
// empty stack”. For instance pop [1;2;3] evaluates to (1,[2;3]).

let pop s = 
    match s with 
    | [] -> raise (System.Exception ("Can't pop empty stack"))
    | h::t -> (h, t) 

pop [1;2;3]

// Declare an F# function remove k l of type
// ’a-> (’a * ’b) list-> (’a * ’b) list when ’a: equality
// that evaluates to a list l′ equal to l except that a pair (k′,v′) in list l where k = k′ is removed. For
// instance remove 1[(1,"v1");(2,"v2");(3,"v3")] evaluatesto[(2,"v2");(3,"v3")].
// Explain what the constraint when ’a: equality in the type for remove means and why it is
// necessary (Tag: build list, empty list, remove list)

let myList: (int * string) list = [(1,"v1");(2,"v2");(3,"v3")]

let rec remove k l = 
    match l with 
    | [] -> []
    | (key, value)::rest -> 
        if key = k then remove k rest 
        else (key, value)::remove k rest // If no match, add current to new list and check the rest of the input list. 

remove 2 myList

// Declare an F# function mapPush k v m of type
// ’a-> ’b-> mapStack<’a,’b>-> mapStack<’a,’b> when ’a: equality
// that evaluates to a new map–stack, where the value v is pushed on the stack that the key k maps
// to in the map–stack m. If the key k does not exists in m, then the pair (k,[v]) is added to m. For
// instance, mapPush ’1’ 2 ex1  evaluates to: 
//  MapStack [('1', [2; 3; 1; 1]); ('2', [1; 3; 1]); ('3', [1; 1; 3]); ('4', [3; 3; 1]); ('5', [2; 3; 4]); ('6', [2; 1; 2])]
// Tag: Add element to list

let rec mapPush k v m =
    match m with
    | MapStack [] -> MapStack [(k, [v])] // The mapStack is empty. We know the value doesnt exist so we just add it. Recursively too. 
    | MapStack ((key, values)::rest) -> // rest=(key, value);(key,value)...
        if key = k then MapStack ((k, v::values) :: rest) // Key exist, the funciton returns a new MapStack with the new value included. 
        else // We have checked all current keys ad input k does not exist, 
            let restMapStack = mapPush k v (MapStack rest) // recurse call with the remaing MapStack, return the mapStack of the rest
            match restMapStack with
            | MapStack newRest -> MapStack ((key, values) :: newRest) // Happens only AFTER the restMapStack is handled. 


mapPush '1' 5 ex1 
mapPush '6' 9 (MapStack [('2',[2]); ('3',[2]); ('8',[2]); ('7',[2])])
// 
// Nederste kald (der hvor noget sker)
// MapStack [('6',[9])]
// -----------------------------------------------------------------
// newRest = [('6',[9])]
// 
// → returnerer:
// MapStack [('7',[2]); ('6',[9])]
// -----------------------------------------------------------------
// newRest = [('7',[2]); ('6',[9])] 
// 
// → returnerer:
// MapStack [('8',[2]); ('7',[2]); ('6',[9])]
// -----------------------------------------------------------------
// newRest = [('8',[2]); ('7',[2]); ('6',[9])] 
// 
// → returnerer:
// MapStack [('3',[2]); ('8',[2]); ('7',[2]); ('6',[9])]
// -----------------------------------------------------------------
// newRest = [('3',[2]); ('8',[2]); ('7',[2]); ('6',[9])] 
// 
// → returnerer:
// MapStack [('2',[2]); ('3',[2]); ('8',[2]); ('7',[2]); ('6',[9])]
// 
// // example with only a list not a MapStack: 
// let rec f list =
//     match list with
//     | [] -> ["NEW"]
//     | x::rest ->
//         x :: f rest
// 
// let elementals = ["earth"; "wind"; "fire"; "water"]        
// f elementals