Class {
	#name : 'HeightAStarSolver',
	#superclass : 'AStarSolver',
	#category : 'AoC2022-Day12',
	#package : 'AoC2022-Day12'
}

{ #category : 'testing' }
HeightAStarSolver >> isWalkable: aPoint [

	| x y curHeight nextHeight diff |
	x := aPoint x.
	y := aPoint y.

	"Check bounds"
	(x < 1 or: [ x > map width ]) ifTrue: [ ^ false ].
	(y < 1 or: [ y > map height ]) ifTrue: [ ^ false ].

	curHeight := map at: current.
	nextHeight := map at: aPoint.

	diff := curHeight - nextHeight.

	^ diff >= -1
]
