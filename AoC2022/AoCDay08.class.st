"
Day08 of Advent of Code 2022.
"
Class {
	#name : 'AoCDay08',
	#superclass : 'AoCBase',
	#instVars : [
		'map'
	],
	#category : 'AoC2022',
	#package : 'AoC2022'
}

{ #category : 'as yet unclassified' }
AoCDay08 >> part1: lines [

	| interiorTrees visible |
	map := self parseToGridAsIntegers: lines.
	visible := map width + map height * 2 - 4.

	interiorTrees := (2 to: map width - 1) flatCollect: [ :c |
		                 (2 to: map height - 1) collect: [ :r | c @ r ] ].

	interiorTrees do: [ :tree |
			| row col treeHeight left right top bottom |
			treeHeight := map at: tree.
			row := (map atRow: tree y) asOrderedCollection.
			col := (map atColumn: tree x) asOrderedCollection.

			left := (row takeFirst: tree x - 1) allSatisfy: [ :otherHeight |
				        otherHeight < treeHeight ].
			right := (row allButFirst: tree x) allSatisfy: [ :otherHeight |
				         otherHeight < treeHeight ].
			top := (col takeFirst: tree y - 1) allSatisfy: [ :otherHeight |
				       otherHeight < treeHeight ].
			bottom := (col allButFirst: tree y) allSatisfy: [ :otherHeight |
				          otherHeight < treeHeight ].

			left | right | top | bottom ifTrue: [ visible := visible + 1 ] ].

	^ visible
]
