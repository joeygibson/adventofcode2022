"
Day10 of Advent of Code 2022.
"
Class {
	#name : 'AoCDay10',
	#superclass : 'AoCBase',
	#instVars : [
		'register',
		'clock'
	],
	#category : 'AoC2022',
	#package : 'AoC2022'
}

{ #category : 'as yet unclassified' }
AoCDay10 >> part1: lines [

	| cycles |
	cycles := (#( 20 60 100 140 180 220 ) collect: [ :cycle | cycle -> 0 ])
		          asDictionary.

	clock := 1.
	register := 1.

	lines do: [ :line |
			(cycles includesKey: clock) ifTrue: [
				cycles at: clock put: register * clock ].

			line = 'noop' ifTrue: [ clock := clock + 1 ].

			(line beginsWith: 'addx') ifTrue: [
					1 to: 2 do: [ :i |
							(cycles includesKey: clock) ifTrue: [
								cycles at: clock put: register * clock ].

							i = 2 ifTrue: [
								register := register + line substrings second asInteger ].

							clock := clock + 1 ] ] ].

	^ cycles values sum
]
