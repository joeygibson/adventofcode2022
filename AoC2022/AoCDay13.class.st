"
Day13 of Advent of Code 2022.
"
Class {
	#name : 'AoCDay13',
	#superclass : 'AoCBase',
	#category : 'AoC2022',
	#package : 'AoC2022'
}

{ #category : 'verification' }
AoCDay13 >> compare: left with: right [

	| result |
	left = right ifTrue: [ ^ #eq ].
	(left isInteger and: [ right isInteger ]) ifTrue: [
			left < right
				ifTrue: [ ^ #lt ]
				ifFalse: [ ^ #gt ] ].
	(left isArray and: [ right isInteger ]) ifTrue: [
		^ self compare: left with: { right } ].
	(left isInteger and: [ right isArray ]) ifTrue: [
		^ self compare: { left } with: right ].

	(left isNotEmpty and: [ right isNotEmpty ]) ifTrue: [
			result := self compare: left first with: right first.
			result = #eq
				ifTrue: [
				^ self compare: left allButFirst with: right allButFirst ]
				ifFalse: [ ^ result ] ].

	left isNotEmpty
		ifTrue: [ ^ #gt ]
		ifFalse: [ ^ #lt ]
]

{ #category : 'as yet unclassified' }
AoCDay13 >> part1: lines [

	| sections results result |
	result := 0.
	sections := lines splitOn: [ :line | line isEmpty ].

	results := sections collect: [ :section |
			           | a b |
			           a := STONJSON fromString: section first.
			           b := STONJSON fromString: section second.

			           self compare: a with: b ].

	results doWithIndex: [ :res :i |
		res = #lt ifTrue: [ result := result + i ] ].

	^ result
]

{ #category : 'as yet unclassified' }
AoCDay13 >> part2: lines [

	| filteredLines sortedPackets |
	filteredLines := ((lines select: [ :line | line isNotEmpty ])
		                  collect: [ :line | STONJSON fromString: line ])
		                 asOrderedCollection.

	filteredLines add: { { 2 } }.
	filteredLines add: { { 6 } }.

	sortedPackets := filteredLines sorted: [ :a :b |
			                 | res |
			                 res := self compare: a with: b.

			                 res = #lt or: res = #eq ].

	^ (sortedPackets find: { { 2 } }) * (sortedPackets find: { { 6 } })
]
