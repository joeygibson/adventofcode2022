"
Day02 of Advent of Code 2022.
"
Class {
	#name : 'AoCDay02',
	#superclass : 'AoCBase',
	#category : 'AoC2022',
	#package : 'AoC2022'
}

{ #category : 'as yet unclassified' }
AoCDay02 >> part1: lines [

	| hands |
	hands := {
		         ('A X' -> 4).
		         ('A Y' -> 8).
		         ('A Z' -> 3).
		         ('B X' -> 1).
		         ('B Y' -> 5).
		         ('B Z' -> 9).
		         ('C X' -> 7).
		         ('C Y' -> 2).
		         ('C Z' -> 6) } asDictionary.

	^ (lines collect: [ :line | hands at: line ]) sum
]
