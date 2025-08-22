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

{ #category : 'as yet unclassified' }
AoCDay12 >> part2: lines [

	| tmpMap aStar starts paths |
	tmpMap := self parseToGridAsStrings: lines.

	start := self find: 'S' inGrid: tmpMap.
	end := self find: 'E' inGrid: tmpMap.
	starts := OrderedCollection new.

	tmpMap withIndexesDo: [ :c :r :val |
		(val = 'a' or: val = 'S') ifTrue: [ starts add: c @ r ] ].

	map := self parseToGrid: lines withConverter: [ :c | c asciiValue ].
	map at: start put: $a asciiValue.
	map at: end put: $z asciiValue.

	paths := starts collect: [ :start |
			         aStar := HeightAStarSolver
				                  withMap: map
				                  startPoint: start
				                  endPoint: end.
			         aStar findShortestPath ].
	paths := paths select: [ :path | path isNotEmpty ].
	paths := paths sorted: [ :a :b | a size < b size ].
	^ paths first size - 1
]
