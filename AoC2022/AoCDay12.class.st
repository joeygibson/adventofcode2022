"
Day12 of Advent of Code 2022.
"
Class {
	#name : 'AoCDay12',
	#superclass : 'AoCBase',
	#instVars : [
		'map',
		'start',
		'end'
	],
	#category : 'AoC2022',
	#package : 'AoC2022'
}

{ #category : 'as yet unclassified' }
AoCDay12 >> part1: lines [

	| tmpMap aStar |
	tmpMap := self parseToGridAsStrings: lines.

	start := self find: 'S' inGrid: tmpMap.
	end := self find: 'E' inGrid: tmpMap.

	map := self parseToGrid: lines withConverter: [ :c | c asciiValue  ].
	map at: start put: $a asciiValue.
	map at: end put: $z asciiValue.

	aStar := HeightAStarSolver
		         withMap: map
		         startPoint: start
		         endPoint: end.

	^ aStar findShortestPath size - 1
]
