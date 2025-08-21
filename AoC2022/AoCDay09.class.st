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
