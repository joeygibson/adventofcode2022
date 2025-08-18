"
Day01 of Advent of Code 2025.
"
Class {
	#name : 'AoCDay01',
	#superclass : 'AoCBase',
	#category : 'AoC2022',
	#package : 'AoC2022'
}

{ #category : 'as yet unclassified' }
AoCDay01 >> part1: lines [

	| elfManifests |
	elfManifests := lines splitOn: [ :line | line isEmpty ].

	^ (elfManifests collect: [ :group |
		   (group collect: [ :calories | calories asInteger ]) sum ]) max
]

{ #category : 'as yet unclassified' }
AoCDay01 >> part2: lines [

	| elfManifests elfTotals |
	elfManifests := lines splitOn: [ :line | line isEmpty ].
	elfTotals := elfManifests collect: [ :group |
		             (group collect: [ :calories | calories asInteger ]) sum ].

	^ ((elfTotals sorted: [ :a :b | a > b ]) takeFirst: 3) sum
]
