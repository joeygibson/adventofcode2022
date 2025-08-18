"
Day04 of Advent of Code 2022.
"
Class {
	#name : 'AoCDay04',
	#superclass : 'AoCBase',
	#category : 'AoC2022',
	#package : 'AoC2022'
}

{ #category : 'as yet unclassified' }
AoCDay04 >> part1: lines [

	| res |
	res := lines select: [ :line |
			       | pairs left right leftSeq rightSeq |
			       pairs := line substrings: ','.
			       left := (pairs first substrings: '-') collect: [ :c |
				               c asInteger ].
			       right := (pairs second substrings: '-') collect: [ :c |
				                c asInteger ].

			       leftSeq := left first to: left second.
			       rightSeq := right first to: right second.

			       (leftSeq includesAll: rightSeq) or: [
				       rightSeq includesAll: leftSeq ] ].

	^ res size
]

{ #category : 'as yet unclassified' }
AoCDay04 >> part2: lines [

	| res |
	res := lines select: [ :line |
			       | pairs left right leftSeq rightSeq |
			       pairs := line substrings: ','.
			       left := (pairs first substrings: '-') collect: [ :c |
				               c asInteger ].
			       right := (pairs second substrings: '-') collect: [ :c |
				                c asInteger ].

			       leftSeq := left first to: left second.
			       rightSeq := right first to: right second.

			       (leftSeq includesAny: rightSeq) or: [
				       rightSeq includesAny: leftSeq ] ].

	^ res size
]
