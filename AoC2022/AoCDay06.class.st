"
Day06 of Advent of Code 2022.
"
Class {
	#name : 'AoCDay06',
	#superclass : 'AoCBase',
	#category : 'AoC2022',
	#package : 'AoC2022'
}

{ #category : 'as yet unclassified' }
AoCDay06 >> part1: lines [

	| input |
	input := lines first.

	^ self processLine: input markerChars: 4
]

{ #category : 'as yet unclassified' }
AoCDay06 >> part2: lines [

	| input |
	input := lines first.

	^ self processLine: input markerChars: 14
]

{ #category : 'as yet unclassified' }
AoCDay06 >> processLine: line markerChars: markerChars [

	| charCounts chars |
	charCounts := Dictionary new.
	chars := OrderedCollection new.

	line doWithIndex: [ :c :index |
			chars add: c.
			charCounts at: c update: [ :v | v + 1 ] initial: [ 1 ].

			chars size = markerChars ifTrue: [
					(charCounts values allSatisfy: [ :v | v = 1 ])
						ifTrue: [ ^ index ]
						ifFalse: [
								| oldest |
								oldest := chars removeFirst.
								(charCounts at: oldest) = 1
									ifTrue: [ charCounts removeKey: oldest ]
									ifFalse: [ charCounts at: oldest update: [ :v | v - 1 ] ] ] ] ]
]
