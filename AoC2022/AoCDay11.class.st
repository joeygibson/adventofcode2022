"
Day11 of Advent of Code 2022.
"
Class {
	#name : 'AoCDay11',
	#superclass : 'AoCBase',
	#instVars : [
		'monkeys'
	],
	#category : 'AoC2022',
	#package : 'AoC2022'
}

{ #category : 'as yet unclassified' }
AoCDay11 >> part1: lines [

	| sortedMonkeys |
	self populateMonkeys: lines.

	20 timesRepeat: [ monkeys do: [ :monkey | monkey takeTurn ] ].


	sortedMonkeys := monkeys sorted: [ :a :b |
		                 b inspectionCount < a inspectionCount ].

	^ sortedMonkeys first inspectionCount
	  * sortedMonkeys second inspectionCount
]

{ #category : 'as yet unclassified' }
AoCDay11 >> part2: lines [

	| sortedMonkeys worryReducer |
	self populateMonkeys: lines.

	worryReducer := monkeys inject: 1 into: [ :total :monkey | monkey divisor * total ].

	10000 timesRepeat: [
		monkeys do: [ :monkey | monkey takeTurn: worryReducer ] ].


	sortedMonkeys := monkeys sorted: [ :a :b |
		                 b inspectionCount < a inspectionCount ].

	^ sortedMonkeys first inspectionCount
	  * sortedMonkeys second inspectionCount
]

{ #category : 'as yet unclassified' }
AoCDay11 >> populateMonkeys: lines [

	| sections |
	sections := lines splitOn: [ :line | line isEmpty ].

	monkeys := sections collect: [ :section | Monkey from: section ].

	sections doWithIndex: [ :section :i |
			| monkey monkeyId |
			monkey := monkeys at: i.

			monkeyId := section fifth substrings last asInteger + 1.
			monkey trueMonkey: (monkeys at: monkeyId).

			monkeyId := section sixth substrings last asInteger + 1.
			monkey falseMonkey: (monkeys at: monkeyId) ]
]
