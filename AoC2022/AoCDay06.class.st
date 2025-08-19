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

	| input charCounts chars |
	charCounts := Dictionary new.
	chars := OrderedCollection new.

	input := lines first.

	input doWithIndex: [ :c :index |
			chars add: c.
			charCounts at: c update: [ :v | v + 1 ] initial: [ 1 ].

			chars size = 4 ifTrue: [
					(charCounts values allSatisfy: [ :v | v = 1 ])
						ifTrue: [ ^ index ]
						ifFalse: [
								| oldest |
								oldest := chars removeFirst.
								(charCounts at: oldest) = 1
									ifTrue: [ charCounts removeKey: oldest ]
									ifFalse: [ charCounts at: oldest update: [ :v | v - 1 ] ] ] ] ]
]
