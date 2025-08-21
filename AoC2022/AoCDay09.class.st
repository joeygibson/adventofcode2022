"
Day09 of Advent of Code 2022.
"
Class {
	#name : 'AoCDay09',
	#superclass : 'AoCBase',
	#category : 'AoC2022',
	#package : 'AoC2022'
}

{ #category : 'as yet unclassified' }
AoCDay09 >> computeStepFrom: point direction: dir [

	dir = 'R' ifTrue: [ ^ 1 @ 0 ].
	dir = 'L' ifTrue: [ ^ -1 @ 0 ].
	dir = 'U' ifTrue: [ ^ 0 @ -1 ].
	dir = 'D' ifTrue: [ ^ 0 @ 1 ]
]

{ #category : 'as yet unclassified' }
AoCDay09 >> part1: lines [

	| visited curHead curTail tmp |
	curHead := 0 @ 0.
	curTail := 0 @ 0.
	visited := Set with: curTail.

	lines do: [ :line |
			| chunks dir steps dist |
			chunks := line substrings.
			dir := chunks first.
			steps := chunks second asInteger.

			steps timesRepeat: [
					tmp := curHead.
					curHead := curHead
					           + (self computeStepFrom: curHead direction: dir).
					dist := curTail distanceTo: curHead.

					(curHead = curTail or: [
						 curHead eightNeighbors includes: curTail ]) ifFalse: [
							curTail := tmp.
							visited add: curTail ] ] ].

	^ visited size
]

{ #category : 'as yet unclassified' }
AoCDay09 >> part2: lines [

	| visited knots |
	knots := ((1 to: 10) collect: [ :i | 0 @ 0 ]) asOrderedCollection.

	visited := Set with: 0 @ 0.

	lines do: [ :line |
			| chunks dir steps |
			chunks := line substrings.
			dir := chunks first.
			steps := chunks second asInteger.

			steps timesRepeat: [
					(1 to: 10) overlappingPairsDo: [ :h :t |
							| head tail tmp |
							head := knots at: h.
							tail := knots at: t.

							tmp := head.
							head := head + (self computeStepFrom: head direction: dir).
							knots at: h put: head.

							(head = tail or: [ head eightNeighbors includes: tail ])
								ifFalse: [
										knots at: t put: tmp.
										t = 10 ifTrue: [ visited add: tmp ] ] ] ] ].

	^ visited size
]
