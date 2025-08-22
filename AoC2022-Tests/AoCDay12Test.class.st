Class {
	#name : 'AoCDay12Test',
	#superclass : 'AoCBaseTest',
	#category : 'AoC2022-Tests',
	#package : 'AoC2022-Tests'
}

{ #category : 'running' }
AoCDay12Test >> setUp [
	super setUp.
	self day: '12'.
	self path: FileLocator home / 'Projects' / 'adventofcode2022' asString.

	inst := AoCDay12 new.
]

{ #category : 'tests' }
AoCDay12Test >> testPart1WithRealData [
|lines res|

	lines := self
		         readInputFileAsLines: 'input1.txt'
		         for: self day
		         onPath: self path.

	res := inst part1: lines.

	self assert: res equals: 383.
]

{ #category : 'tests' }
AoCDay12Test >> testPart1WithSample [
|lines res|

	lines := self
		         readInputFileAsLines: 'input0.txt'
		         for: self day
		         onPath: self path.

	res := inst part1: lines.

	self assert: res equals: 31.
]

{ #category : 'tests' }
AoCDay12Test >> testPart2WithRealData [
|lines res|

	lines := self
		         readInputFileAsLines: 'input1.txt'
		         for: self day
		         onPath: self path.

	res := inst part2: lines.

	self assert: res equals: 377.
]

{ #category : 'tests' }
AoCDay12Test >> testPart2WithSample [
|lines res|

	lines := self
		         readInputFileAsLines: 'input0.txt'
		         for: self day
		         onPath: self path.

	res := inst part2: lines.

	self assert: res equals: 29.
]
