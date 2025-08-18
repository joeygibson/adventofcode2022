"
Day02 of Advent of Code 2022.
"
Class {
	#name : 'AoCDay02',
	#superclass : 'AoCBase',
	#instVars : [
		'hands'
	],
	#category : 'AoC2022',
	#package : 'AoC2022'
}

{ #category : 'initialization' }
AoCDay02 >> initialize [

	super initialize.

	hands := {
		         ('A X' -> 4).
		         ('A Y' -> 8).
		         ('A Z' -> 3).
		         ('B X' -> 1).
		         ('B Y' -> 5).
		         ('B Z' -> 9).
		         ('C X' -> 7).
		         ('C Y' -> 2).
		         ('C Z' -> 6) } asDictionary
]

{ #category : 'as yet unclassified' }
AoCDay02 >> part1: lines [

	^ (lines collect: [ :line | hands at: line ]) sum
]

{ #category : 'as yet unclassified' }
AoCDay02 >> part2: lines [

	| updatedLines |
	updatedLines := lines collect: [ :line |
			                line
				                caseOf: {
						                ([ 'A X' ] -> [ 'A Z' ]).
						                ([ 'A Y' ] -> [ 'A X' ]).
						                ([ 'A Z' ] -> [ 'A Y' ]).
						                ([ 'B X' ] -> [ 'B X' ]).
						                ([ 'B Y' ] -> [ 'B Y' ]).
						                ([ 'B Z' ] -> [ 'B Z' ]).
						                ([ 'C X' ] -> [ 'C Y' ]).
						                ([ 'C Y' ] -> [ 'C Z' ]).
						                ([ 'C Z' ] -> [ 'C X' ]) }
				                otherwise: [ self error: 'Invalid item' ] ].


	^ (updatedLines collect: [ :line | hands at: line ]) sum
]
