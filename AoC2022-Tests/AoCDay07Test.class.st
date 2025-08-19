Class {
	#name : 'AoCDay07Test',
	#superclass : 'AoCBaseTest',
	#category : 'AoC2022-Tests',
	#package : 'AoC2022-Tests'
}

{ #category : 'running' }
AoCDay07Test >> setUp [
	super setUp.
	self day: '7'.
	self path: FileLocator home / 'Projects' / 'adventofcode2022' asString.

	inst := AoCDay07 new.
]

{ #category : 'tests' }
AoCDay07Test >> testPart1WithRealData [
|lines res|

	lines := self
		         readInputFileAsLines: 'input1.txt'
		         for: self day
		         onPath: self path.

	res := inst part1: lines.

	self assert: res equals: 1307902.
]

{ #category : 'tests' }
AoCDay07Test >> testPart1WithSample [
|lines res|

	lines := self
		         readInputFileAsLines: 'input0.txt'
		         for: self day
		         onPath: self path.

	res := inst part1: lines.

	self assert: res equals: 95437.
]

{ #category : 'tests' }
AoCDay07Test >> testPart2WithRealData [
|lines res|

	lines := self
		         readInputFileAsLines: 'input1.txt'
		         for: self day
		         onPath: self path.

	res := inst part2: lines.

	self assert: res equals: 7068748.
]

{ #category : 'tests' }
AoCDay07Test >> testPart2WithSample [
|lines res|

	lines := self
		         readInputFileAsLines: 'input0.txt'
		         for: self day
		         onPath: self path.

	res := inst part2: lines.

	self assert: res equals: 24933642.
]
