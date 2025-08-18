Class {
	#name : 'AoCDay01Test',
	#superclass : 'AoCBaseTest',
	#category : 'AoC2022-Tests',
	#package : 'AoC2022-Tests'
}

{ #category : 'running' }
AoCDay01Test >> setUp [
	super setUp.
	self day: '1'.
	self path: FileLocator home / 'Projects' / 'adventofcode2022' asString.

	inst := AoCDay01 new.
]

{ #category : 'tests' }
AoCDay01Test >> testPart1WithRealData [
|lines res|

	lines := self
		         readInputFileAsLines: 'input1.txt'
		         for: self day
		         onPath: self path.

	res := inst part1: lines.

	self assert: res equals: 69310.
]

{ #category : 'tests' }
AoCDay01Test >> testPart1WithSample [
|lines res|

	lines := self
		         readInputFileAsLines: 'input0.txt'
		         for: self day
		         onPath: self path.

	res := inst part1: lines.

	self assert: res equals: 24000.
]

{ #category : 'tests' }
AoCDay01Test >> testPart2WithRealData [
|lines res|

	lines := self
		         readInputFileAsLines: 'input1.txt'
		         for: self day
		         onPath: self path.

	res := inst part2: lines.

	self assert: res equals: 206104.
]

{ #category : 'tests' }
AoCDay01Test >> testPart2WithSample [
|lines res|

	lines := self
		         readInputFileAsLines: 'input0.txt'
		         for: self day
		         onPath: self path.

	res := inst part2: lines.

	self assert: res equals: 45000.
]
